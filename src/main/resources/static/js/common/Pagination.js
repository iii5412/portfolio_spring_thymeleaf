import {createEl, qsAll} from "/js/common/util.js";

export default class Pagination {
    container;
    page = 1;
    size;
    totalCount;
    pageCnt;
    onClickPageButton = () => {};

    constructor(container) {
        this.container = container;
    }

    getCurrentPage() {
        return this.page;
    }

    create({page, size, totalCount} = {page : 10, size : 1, totalCount : 0}) {
        this.#clear();
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;
        this.pageCnt = Math.ceil(totalCount / size);

        this.#draw();
    }

    #clear() {
        this.container.innerHTML = '';
    }

    #pageButtonOn(page) {
        page = String(page);
        qsAll(this.container, '.pagination-button').forEach(btn => {
            if(btn.dataset.page !== page){
                btn.classList.remove('on');
            } else {
                btn.classList.add('on');
            }
        })
    }

    #onClickPageButton(currentPage) {
        this.onClickPageButton(currentPage);
    }

    #draw() {
        const paginationContainer = createEl('div');
        paginationContainer.classList.add('pagination-container');

        for (let i = 1; i <= this.pageCnt; i++) {
            const pageButton = createEl('input', {type: 'button', value: i});
            pageButton.classList.add('pagination-button');
            pageButton.dataset.page = i;
            pageButton.addEventListener('click', () => {
                this.page = i;
                this.#pageButtonOn(i);
                this.#onClickPageButton(i);
            });
            paginationContainer.appendChild(pageButton);
        }

        this.container.appendChild(paginationContainer);

        this.#pageButtonOn(1);
    }


}
