import { defineComponent, resolveComponent, openBlock, createElementBlock, normalizeClass, createBlock, createCommentVNode } from 'vue';
import '../../../hooks/index.mjs';
import ImgPlaceholder from './image-placeholder.mjs';
import { skeletonItemProps } from './skeleton-item.mjs';
import _export_sfc from '../../../_virtual/plugin-vue_export-helper.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';

const _sfc_main = defineComponent({
  name: "ElSkeletonItem",
  components: {
    ImgPlaceholder
  },
  props: skeletonItemProps,
  setup() {
    const ns = useNamespace("skeleton");
    return {
      ns
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_img_placeholder = resolveComponent("img-placeholder");
  return openBlock(), createElementBlock("div", {
    class: normalizeClass([_ctx.ns.e("item"), _ctx.ns.e(_ctx.variant)])
  }, [
    _ctx.variant === "image" ? (openBlock(), createBlock(_component_img_placeholder, { key: 0 })) : createCommentVNode("v-if", true)
  ], 2);
}
var SkeletonItem = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render]]);

export { SkeletonItem as default };
//# sourceMappingURL=skeleton-item2.mjs.map
