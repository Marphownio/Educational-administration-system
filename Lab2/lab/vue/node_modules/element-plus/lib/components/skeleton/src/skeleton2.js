'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../hooks/index.js');
var skeletonItem = require('./skeleton-item2.js');
var skeleton = require('./skeleton.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var index = require('../../../hooks/use-namespace/index.js');
var index$1 = require('../../../hooks/use-throttle-render/index.js');

const _sfc_main = vue.defineComponent({
  name: "ElSkeleton",
  components: {
    [skeletonItem["default"].name]: skeletonItem["default"]
  },
  props: skeleton.skeletonProps,
  setup(props) {
    const ns = index.useNamespace("skeleton");
    const innerLoading = vue.computed(() => {
      return props.loading;
    });
    const uiLoading = index$1.useThrottleRender(innerLoading, props.throttle);
    return {
      ns,
      uiLoading
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_el_skeleton_item = vue.resolveComponent("el-skeleton-item");
  return _ctx.uiLoading ? (vue.openBlock(), vue.createElementBlock("div", vue.mergeProps({
    key: 0,
    class: [_ctx.ns.b(), _ctx.ns.is("animated", _ctx.animated)]
  }, _ctx.$attrs), [
    (vue.openBlock(true), vue.createElementBlock(vue.Fragment, null, vue.renderList(_ctx.count, (i) => {
      return vue.openBlock(), vue.createElementBlock(vue.Fragment, { key: i }, [
        _ctx.loading ? vue.renderSlot(_ctx.$slots, "template", { key: i }, () => [
          vue.createVNode(_component_el_skeleton_item, {
            class: vue.normalizeClass(_ctx.ns.is("first")),
            variant: "p"
          }, null, 8, ["class"]),
          (vue.openBlock(true), vue.createElementBlock(vue.Fragment, null, vue.renderList(_ctx.rows, (item) => {
            return vue.openBlock(), vue.createBlock(_component_el_skeleton_item, {
              key: item,
              class: vue.normalizeClass([
                _ctx.ns.e("paragraph"),
                _ctx.ns.is("last", item === _ctx.rows && _ctx.rows > 1)
              ]),
              variant: "p"
            }, null, 8, ["class"]);
          }), 128))
        ]) : vue.createCommentVNode("v-if", true)
      ], 64);
    }), 128))
  ], 16)) : vue.renderSlot(_ctx.$slots, "default", vue.normalizeProps(vue.mergeProps({ key: 1 }, _ctx.$attrs)));
}
var Skeleton = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["render", _sfc_render]]);

exports["default"] = Skeleton;
//# sourceMappingURL=skeleton2.js.map
