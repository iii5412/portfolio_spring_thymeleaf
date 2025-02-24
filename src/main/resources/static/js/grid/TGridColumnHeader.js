export default class TGridColumnHeader {
    static VERTICAL_ALIGN = {
        TOP: 'top',
        MIDDLE: 'middle',
        BOTTOM: 'bottom',
    };
    static ALIGN = {
        LEFT: 'left',
        CENTER: 'center',
        RIGHT: 'right',
    }

    /**
     * @type {string}
     */
    #name;
    /**
     * @type {string}
     */
    #valign = TGridColumnHeader.VERTICAL_ALIGN.MIDDLE;
    /**
     * @type {string}
     */
    #align = TGridColumnHeader.ALIGN.CENTER;

    constructor(name) {
        this.#name = name;
    }

    setValign(valign) {
        this.#valign = valign;
    }

    setAlign(align) {
        this.#align = align;
    }

    /**
     *
     * @return {{name: string, valign: string, align: string}}
     */
    getHeaderInfo() {
        return {
            name: this.#name,
            valign: this.#valign,
            align: this.#align,
        }
    }
}
