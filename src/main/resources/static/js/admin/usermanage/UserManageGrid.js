export default class UserManageGrid {
    container;
    gridId;

    gridView;
    gridProvider;

    /**
     *
     * @param {HTMLElement} container
     * @param {string} gridId
     */
    constructor(container, gridId) {
        this.container = container;
        this.gridId = gridId;
        this.#renderGrid();
    }

    async #renderGrid() {
        await this.#getUsers();
    }

    async #getUsers() {

    }
}
