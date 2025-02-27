import { isEmptyObject, qs } from '/js/common/util.js';
import ProgramSearchRequestDto from '/js/apis/program/request/program-manage-search.request.dto.js';
import Program from '/js/program/Program.js';
import { fetchManageProgram } from '/js/apis/program/program';

const tag = '[ProgramGrid]';
/**
 * @description 프로그램을 관리하는 그리드 클래스입니다.
 * @class ProgramGrid
 * @remarks searchForm내의 검색조건은    SearchType과 SearchInput으로 id가 구성되어야함.
 */
export default class ProgramGrid {
    /**
     * @type {TGrid}
     */
    grid;
    /**
     * @type {HTMLElement}
     */
    container;
    /**
     * @type {HTMLElement}
     */
    searchForm;
    /**
     * @type {TPagination}
     */
    pagination;
    /**
     * @type {{}}
     */
    searchParam = {};
    /**
     *
     * @type {number}
     */
    size = 20;

    /**
     * @param {object} paramObj
     * @param {TGrid} paramObj.tGrid
     * @param {HTMLElement} paramObj.container
     * @param {HTMLElement} paramObj.searchForm - 검색 양식 요소입니다.
     * @param {TPagination} paramObj.pagination - 페이지네이션 요소입니다.
     */
    constructor({
        tGrid,
        container,
        searchForm,
        pagination,
    }) {
        this.grid = tGrid;
        this.container = container;
        this.searchForm = searchForm;
        this.pagination = pagination;
        this.#init();
    }

    #init() {
        this.#bindPaginationEvent();
    }

    #bindPaginationEvent() {
        const handleBeforeMove = async (eventData) => {
            const { page } = eventData;
            if (page !== this.pagination.getCurrentPage())
                await this.search({ page });
        };

        this.pagination.onBeforeMove(handleBeforeMove);
    }

    /**
     * @description 그리드 클릭 이벤트를 설정한다.
     * @param {function} func
     * @param {number} func.param.rowNum - 셀 클릭 이벤트의 파라미터 객체
     * @param {string} func.param.columnName - 클릭된 셀의 열 이름
     */
    setClickEvent(func) {
        this.grid.setCellClick(func);
    }

    /**
     * @description 행 번호에 해당하는 데이터를 Program으로 반환한다.
     * @param rowNum
     * @return {Program|null}
     */
    getProgram(rowNum) {
        const rowData = this.grid.getRowData(rowNum);

        if (isEmptyObject(rowData))
            return null;

        return new Program(rowData);
    }

    /**
     * @override
     * @description 그리드를 조회한다.
     * @return {Promise<void>}
     */
    async search({ page = 1 } = {}) {
        {
            const searchType = qs(this.searchForm, '#searchType').value;
            const searchInput = qs(this.searchForm, '#searchInput').value;
            const searchParam = {
                [searchType]: searchInput,
                page,
                size: 10,
            };

            const programSearchResponseDto = await this.#fetchProgram(searchParam);
            const programResponseDtos = programSearchResponseDto.getResult();
            const totalCount = programSearchResponseDto.getTotalCount();
            const _page = programSearchResponseDto.getPage();
            const size = programSearchResponseDto.getSize();

            this.grid.setData(programResponseDtos.map(programResponseDto => programResponseDto.toObject()));

            this.pagination.setSize(size);
            this.pagination.setTotalCount(totalCount);
            this.pagination.setPage(_page);

        }
    }

    /**
     *
     * @param {object} paramObj
     * @param {number} [paramObj.id]
     * @param {string} [paramObj.programName]
     * @param {string} [paramObj.url]
     * @return {Promise<ProgramSearchResponseDto>}
     */
    async #fetchProgram({ id, programName, url, size, page }) {
        const programSearchRequestDto = new ProgramSearchRequestDto(
            {
                id,
                programName,
                url,
                page: page ? page : 1,
                size: size ? size : 10,
            });
        return await fetchManageProgram(programSearchRequestDto);
    }
}
