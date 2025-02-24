import Grid from "/js/grid/Grid.js";
import TGridColumnHeader from "/js/grid/TGridColumnHeader.js";

export default class TGrid extends Grid {
    tuiGrid;

    /**
     *
     * @param {function} tuiGrid
     */
    constructor(tuiGrid) {
        super();
        this.tuiGrid = tuiGrid;
    }

    /**
     *
     * @param {TGridColumn[]} columns
     */
    setColumns(columns) {
        const columnInfos = columns.map(column => column.getColumnInfo());
        this.tuiGrid.setColumns(columnInfos);
    }

    /**
     *
     * @param {object[]} data
     */
    setData(data = []) {
        this.tuiGrid.resetData(data);
    }

    /**
     * @description 셀 클릭 이벤트를 설정한다.
     * @param {function} func - 셀 클릭 시 호출될 함수
     * @param {Object} func.param - 셀 클릭 이벤트의 파라미터 객체
     * @param {number} func.param.rowNum - 클릭된 셀의 행 번호
     * @param {string} func.param.columnName - 클릭된 셀의 열 이름
     */
    setCellClick(func) {
        this.tuiGrid.on('click', (ev) => {
            const {rowKey, columnName} = ev;
            func({rowNum : rowKey, columnName});
        })
    }

    /**
     * @description 행 번호로 해당 행의 데이터를 가져온다.
     * @param {number} rowNum
     * @return {Object}
     */
    getRowData(rowNum) {
        const row = this.tuiGrid.getRow(rowNum);
        if(row) {
            return row;
        } else {
            console.warn(`[getRowData] 존재하지 않는 행 번호 입니다. rownum => ${rowNum}`);
            return {};
        }
    }

    /**
     * @param {Object} param
     * @param {function} param.tuiGrid
     * @param {HTMLElement} param.wrapperElement
     * @param {TGridColumn[]} param.columns
     * @return {Builder}
     */
    static create({
                      tuiGrid,
                      wrapperElement,
                      columns = []
                  }) {
        const columnAndHeaderInfo = columns.reduce((result, column) => {
            result.columns.push(column.getColumnInfo());
            result.headers.push(column.getHeaderInfo());
            return result;
        }, {columns: [], headers: []});

        const _tuiGrid = new tuiGrid(
                {
                    el: wrapperElement,
                    columns: columnAndHeaderInfo.columns,
                    header: {
                        align: TGridColumnHeader.ALIGN.CENTER,
                        valign: TGridColumnHeader.VERTICAL_ALIGN.MIDDLE,
                        columns: columnAndHeaderInfo.headers,
                    }
                }
        );
        const tGrid = new TGrid(_tuiGrid);
        return tGrid._builder();
    }

    _builder() {
        class Builder {
            /**
             * @type {TGrid}
             */
            tGrid;

            /**
             *
             * @param {TGrid} tGrid
             */
            constructor(tGrid) {
                this.tGrid = tGrid;
            }

            /**
             * 설정을 마무리하고 반영된 tgrid를 반환한다.
             * @return {TGrid}
             */
            end() {
                return this.tGrid;
            }
        }

        return new Builder(this);
    }
}
