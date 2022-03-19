import { defineComponent, computed, openBlock, createElementBlock, normalizeClass, normalizeStyle, renderSlot, createCommentVNode } from 'vue';
import '../../../hooks/index.mjs';
import { dividerProps } from './divider.mjs';
import _export_sfc from '../../../_virtual/plugin-vue_export-helper.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';

const _sfc_main = defineComponent({
  name: "ElDivider",
  props: dividerProps,
  setup(props) {
    const ns = useNamespace("divider");
    const dividerStyle = computed(() => {
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
  return openBlock(), createElementBlock("div", {
    class: normalizeClass([_ctx.ns.b(), _ctx.ns.m(_ctx.direction)]),
    style: normalizeStyle(_ctx.dividerStyle)
  }, [
    _ctx.$slots.default && _ctx.direction !== "vertical" ? (openBlock(), createElementBlock("div", {
      key: 0,
      class: normalizeClass([_ctx.ns.e("text"), _ctx.ns.is(_ctx.contentPosition)])
    }, [
      renderSlot(_ctx.$slots, "default")
    ], 2)) : createCommentVNode("v-if", true)
  ], 6);
}
var Divider = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render]]);

export { Divider as default };
//# sourceMappingURL=divider2.mjs.map
