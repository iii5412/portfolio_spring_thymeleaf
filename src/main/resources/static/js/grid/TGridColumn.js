import TGridColumnHeader from "/js/grid/TGridColumnHeader.js";

export default class TGridColumn {
    static ALIGN = {
        LEFT: 'left',
        CENTER: 'center',
        RIGHT: 'right',
    }

    /**
     * @type {string}
     */
    name;
    /**
     * @type {string}
     */
    headerName;
    /**
     * @type {string}
     */
    editor;
    /**
     * @type {number}
     */
    width;
    /**
     * @type {TGridColumn.ALIGN}
     */
    align = TGridColumn.ALIGN.CENTER;
    /**
     * @type {TGridColumnHeader}
     */
    header;

    constructor(name, headerName) {
        this.name = name;
        this.headerName = headerName;
        this.header = new TGridColumnHeader(name);
    }

    /**
     *
     * @param {string | object} editor
     * @private
     */
    _setEditor(editor) {
        this.editor = editor;
    }

    /**
     *
     * @param {number} width
     * @private
     */
    _setWidth(width) {
        this.width = width;
    }

    /**
     *
     * @param {TGridColumn.ALIGN} align
     * @private
     */
    _setAlign(align) {
        this.align = align;
    }

    /**
     *
     * @param {TGridColumnHeader.VERTICAL_ALIGN} verticalAlign
     * @private
     */
    _setHeaderVerticalAlign(verticalAlign) {
        this.header.setHeaderValign(verticalAlign);
    }

    /**
     *
     * @param {TGridColumnHeader.ALIGN} align
     * @private
     */
    _setHeaderAlign(align) {
        this.header.setHeaderAlign(align);
    }

    /**
     * column정보를 object로 반환한다.
     * @return {{editor: string, name: string, width: number, headerName: string}}
     */
    getColumnInfo() {
        return {
            header: this.headerName,
            name: this.name,
            editor: this.editor,
            width: this.width,
            align: this.align,
        }
    }

    /**
     * Header 설정을 반환한다.
     * @return {{name: string, valign: string, align: string}}
     */
    getHeaderInfo() {
        return this.header.getHeaderInfo();
    }

    _builder() {
        class TGridBuilder {
            /**
             * @type {TGridColumn}
             */
            tGridColumn;

            /**
             *
             * @param {TGridColumn} tGridColumn
             */
            constructor(tGridColumn) {
                this.tGridColumn = tGridColumn;
            }

            /**
             *
             * @return {TGridBuilder}
             */
            text() {
                this.tGridColumn._setEditor('text');
                return this;
            }

            /**
             *
             * @param {number} width
             * @return {TGridBuilder}
             */
            width(width) {
                this.tGridColumn._setWidth(width);
                return this;
            }

            /**
             * 컬럼의 align을 설정한다
             * @return {TGridBuilder}
             */
            alignLeft() {
                this.tGridColumn._setAlign(TGridColumn.ALIGN.LEFT);
                return this;
            }

            /**
             * 컬럼의 align을 설정한다
             * @return {TGridBuilder}
             */
            alignRight() {
                this.tGridColumn._setAlign(TGridColumn.ALIGN.RIGHT);
                return this;
            }

            /**
             * 컬럼의 vertical Align을 설정한다
             * @param {TGridColumnHeader.VERTICAL_ALIGN} verticalAlign
             */
            headerVerticalAlign(verticalAlign) {
                this.tGridColumn._setHeaderVerticalAlign(verticalAlign);
                return this;
            }

            /**
             * 컬럼의 align을 설정한다
             * @param {TGridColumnHeader.ALIGN} align
             * @return {TGridBuilder}
             */
            headerAlign(align) {
                this.tGridColumn._setHeaderAlign(align);
                return this;
            }

            /**
             *
             * @return {TGridColumn}
             */
            end() {
                return this.tGridColumn;
            }
        }

        return new TGridBuilder(this);
    }

    /**
     *
     * @param {Object} param
     * @param {string} param.name
     * @param {string} param.header
     * @return {TGridBuilder}
     */
    static create({name, header}) {
        const tGridColumn = new TGridColumn(name, header);
        return tGridColumn._builder();
    }
}
