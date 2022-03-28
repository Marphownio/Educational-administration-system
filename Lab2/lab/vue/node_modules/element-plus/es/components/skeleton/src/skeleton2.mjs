import { defineComponent, computed, resolveComponent, openBlock, createElementBlock, mergeProps, Fragment, renderList, renderSlot, createVNode, normalizeClass, createBlock, createCommentVNode, normalizeProps } from 'vue';
import '../../../hooks/index.mjs';
import SkeletonItem from './skeleton-item2.mjs';
import { skeletonProps } from './skeleton.mjs';
import _export_sfc from '../../../_virtual/plugin-vue_export-helper.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';
import { useThrottleRender } from '../../../hooks/use-throttle-render/index.mjs';

const _sfc_main = defineComponent({
  name: "ElSkeleton",
  components: {
    [SkeletonItem.name]: SkeletonItem
  },
  props: skeletonProps,
  setup(props) {
    const ns = useNamespace("skeleton");
    const innerLoading = computed(() => {
      return props.loading;
    });
    const uiLoading = useThrottleRender(innerLoading, props.throttle);
    return {
      ns,
      uiLoading
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_el_skeleton_item = resolveComponent("el-skeleton-item");
  return _ctx.uiLoading ? (openBlock(), createElementBlock("div", mergeProps({
    key: 0,
    class: [_ctx.ns.b(), _ctx.ns.is("animated", _ctx.animated)]
  }, _ctx.$attrs), [
    (openBlock(true), createElementBlock(Fragment, null, renderList(_ctx.count, (i) => {
      return openBlock(), createElementBlock(Fragment, { key: i }, [
        _ctx.loading ? renderSlot(_ctx.$slots, "template", { key: i }, () => [
          createVNode(_component_el_skeleton_item, {
            class: normalizeClass(_ctx.ns.is("first")),
            variant: "p"
          }, null, 8, ["class"]),
          (openBlock(true), createElementBlock(Fragment, null, renderList(_ctx.rows, (item) => {
            return openBlock(), createBlock(_component_el_skeleton_item, {
              key: item,
              class: normalizeClass([
                _ctx.ns.e("paragraph"),
                _ctx.ns.is("last", item === _ctx.rows && _ctx.rows > 1)
              ]),
              variant: "p"
            }, null, 8, ["class"]);
          }), 128))
        ]) : createCommentVNode("v-if", true)
      ], 64);
    }), 128))
  ], 16)) : renderSlot(_ctx.$slots, "default", normalizeProps(mergeProps({ key: 1 }, _ctx.$attrs)));
}
var Skeleton = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render]]);

export { Skeleton as default };
//# sourceMappingURL=skeleton2.mjs.map
