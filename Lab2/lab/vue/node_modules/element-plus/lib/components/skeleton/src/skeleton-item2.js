'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../hooks/index.js');
var imagePlaceholder = require('./image-placeholder.js');
var skeletonItem = require('./skeleton-item.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var index = require('../../../hooks/use-namespace/index.js');

const _sfc_main = vue.defineComponent({
  name: "ElSkeletonItem",
  components: {
    ImgPlaceholder: imagePlaceholder["default"]
  },
  props: skeletonItem.skeletonItemProps,
  setup() {
    const ns = index.useNamespace("skeleton");
    return {
      ns
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_img_placeholder = vue.resolveComponent("img-placeholder");
  return vue.openBlock(), vue.createElementBlock("div", {
    class: vue.normalizeClass([_ctx.ns.e("item"), _ctx.ns.e(_ctx.variant)])
  }, [
    _ctx.variant === "image" ? (vue.openBlock(), vue.createBlock(_component_img_placeholder, { key: 0 })) : vue.createCommentVNode("v-if", true)
  ], 2);
}
var SkeletonItem = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["render", _sfc_render]]);

exports["default"] = SkeletonItem;
//# sourceMappingURL=skeleton-item2.js.map
