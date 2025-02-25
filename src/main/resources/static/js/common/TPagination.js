export default class TPagination {
    /**
     * @type {function}
     */
    tuiPageNation;
    /**
     * @type {HTMLElement}
     */
    container;

    /**
     * @param {Object} param
     * @param {function} param.tuiPageNation
     * @param {HTMLElement} param.paginationContainer
     */
    constructor({
                    tuiPageNation
                    , paginationContainer
                }) {
        this.tuiPageNation = tuiPageNation;
        this.container = paginationContainer;
    }

    onBeforeMove(callback) {
        this.tuiPageNation.on('beforeMove', (eventData) => {
            callback(eventData);
        });
    }

    onAfterMove(callback) {
        this.tuiPageNation.on('afterMove', (eventData) => {
            callback(eventData);
        });
    }

    /**
     * @description 현재 페이지를 반환한다.
     * @return {number}
     */
    getCurrentPage() {
        return this.tuiPageNation.getCurrentPage();
    }

    /**
     * @description 데이터 총 개수를 설정한다.
     * @param {number} totalCount
     */
    setTotalCount(totalCount) {
        this.tuiPageNation.setTotalItems(totalCount);
    }

    /**
     * @description 페이지를 설정한다
     * @param {number} page
     */
    setPage(page) {
        this.tuiPageNation.movePageTo(page);
    }

    moveToPage(page) {
        this.tuiPageNation.movePageTo(page);
    }

    /**
     * @description 페이지 사이즈를 설정한다.
     * @param {number} size
     */
    setSize(size) {
        this.tuiPageNation.setItemsPerPage(size);
    }


    static create({
                      tuiPageNation
                      , paginationContainer
                  }) {
        class _builder {
            tuiPageNation;
            paginationContainer;
            options = {
                page: 1,
                itemPerPage: 10,
                visiblePages: 5,
                centerAlign: true,
            };

            /**
             * @param {Object} param
             * @param {function} param.tuiPageNation
             * @param {HTMLElement} param.paginationContainer
             */
            constructor({tuiPageNation, paginationContainer}) {
                this.tuiPageNation = tuiPageNation;
                this.paginationContainer = paginationContainer;
            }

            // setTotalItems(totalCount) {
            //     this.options.totalItems = totalCount;
            //     return this;
            // }

            setItemPerPage(size) {
                this.options.itemsPerPage = size;
                return this;
            }

            setVisiblePages(visiblePages) {
                this.options.visiblePages = visiblePages;
                return this;
            }


            end() {
                return new TPagination({
                    tuiPageNation: new this.tuiPageNation(this.paginationContainer, this.options),
                    paginationContainer
                });
            }
        }

        return new _builder({
            tuiPageNation,
            paginationContainer,
        });

    }
}
