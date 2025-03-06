export default class MenuManageGrid {
    /**
     * @type {TGrid}
     */
    tGrid;
    constructor(tGrid) {
        this.tGrid = tGrid;
    }

    /**
     *
     * @param {ManageMenu[]} manageMenus
     */
    setData(manageMenus) {
        this.tGrid.setData(this.convertTGridTreeData(manageMenus));
    }

    /**
     *
     * @param manageMenus
     * @returns {TGridTreeData[]}
     */
    convertTGridTreeData = (manageMenus = []) => {
        /**
         *
         * @param {ManageMenu} manageMenu
         * @returns {TGridTreeData}
         */
        const toObject = (manageMenu) => {
            return {
                id: manageMenu.getId(),
                upperMenuId: manageMenu.getUpperId(),
                menuName: manageMenu.getMenuName(),
                programId: manageMenu.getProgramId(),
                programName: manageMenu.getMenuName(),
                menuType: manageMenu.getMenuType(),
                orderNum: manageMenu.getOrderNum(),
            };
        };
        return manageMenus.map(manageMenu => {
            const manageMenuObject = toObject(manageMenu);
            if (manageMenu.hasChildren()) {
                const subMenus = manageMenu.getSubMenus();
                manageMenuObject._children = this.convertTGridTreeData(subMenus);
            }
            return manageMenuObject;
        });
    };
}
