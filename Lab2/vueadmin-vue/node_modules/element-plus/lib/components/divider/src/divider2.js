'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../hooks/index.js');
var divider = require('./divider.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var index = require('../../../hooks/use-namespace/index.js');

const _sfc_main = vue.defineComponent({
  name: "ElDivider",
  props: divider.dividerProps,
  setup(props) {
    const ns = index.useNamespace("divider");
    const dividerStyle = vue.computed(() => {
      return {
        "--el-border-style": props.borderStyle
      };
    });
    return {
      ns,
      dividerStyle
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return vue.openBlock(), vue.createElementBlock("div", {
    class: vue.normalizeClass([_ctx.ns.b(), _ctx.ns.m(_ctx.direction)]),
    style: vue.normalizeStyle(_ctx.dividerStyle)
  }, [
    _ctx.$slots.default && _ctx.direction !== "vertical" ? (vue.openBlock(), vue.createElementBlock("div", {
      key: 0,
      class: vue.normalizeClass([_ctx.ns.e("text"), _ctx.ns.is(_ctx.contentPosition)])
    }, [
      vue.renderSlot(_ctx.$slots, "default")
    ], 2)) : vue.createCommentVNode("v-if", true)
  ], 6);
}
var Divider = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["render", _sfc_render]]);

exports["default"] = Divider;
//# sourceMappingURL=divider2.js.map
