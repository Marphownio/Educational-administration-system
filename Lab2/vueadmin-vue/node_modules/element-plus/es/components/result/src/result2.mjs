import { defineComponent, computed, openBlock, createElementBlock, normalizeClass, createElementVNode, renderSlot, createBlock, resolveDynamicComponent, createCommentVNode, toDisplayString } from 'vue';
import '../../../hooks/index.mjs';
import { resultProps, IconMap, IconComponentMap } from './result.mjs';
import _export_sfc from '../../../_virtual/plugin-vue_export-helper.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';

const _sfc_main = defineComponent({
  name: "ElResult",
  props: resultProps,
  setup(props) {
    const ns = useNamespace("result");
    const resultIcon = computed(() => {
      const icon = props.icon;
      const iconClass = icon && IconMap[icon] ? IconMap[icon] : "icon-info";
      const iconComponent = IconComponentMap[iconClass] || IconComponentMap["icon-info"];
      return {
        class: iconClass,
        component: iconComponent
      };
    });
    return {
      ns,
      resultIcon
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return openBlock(), createElementBlock("div", {
    class: normalizeClass(_ctx.ns.b())
  }, [
    createElementVNode("div", {
      class: normalizeClass(_ctx.ns.e("icon"))
    }, [
      renderSlot(_ctx.$slots, "icon", {}, () => [
        _ctx.resultIcon.component ? (openBlock(), createBlock(resolveDynamicComponent(_ctx.resultIcon.component), {
          key: 0,
          class: normalizeClass(_ctx.resultIcon.class)
        }, null, 8, ["class"])) : createCommentVNode("v-if", true)
      ])
    ], 2),
    _ctx.title || _ctx.$slots.title ? (openBlock(), createElementBlock("div", {
      key: 0,
      class: normalizeClass(_ctx.ns.e("title"))
    }, [
      renderSlot(_ctx.$slots, "title", {}, () => [
        createElementVNode("p", null, toDisplayString(_ctx.title), 1)
      ])
    ], 2)) : createCommentVNode("v-if", true),
    _ctx.subTitle || _ctx.$slots.subTitle ? (openBlock(), createElementBlock("div", {
      key: 1,
      class: normalizeClass(_ctx.ns.e("subtitle"))
    }, [
      renderSlot(_ctx.$slots, "subTitle", {}, () => [
        createElementVNode("p", null, toDisplayString(_ctx.subTitle), 1)
      ])
    ], 2)) : createCommentVNode("v-if", true),
    _ctx.$slots.extra ? (openBlock(), createElementBlock("div", {
      key: 2,
      class: normalizeClass(_ctx.ns.e("extra"))
    }, [
      renderSlot(_ctx.$slots, "extra")
    ], 2)) : createCommentVNode("v-if", true)
  ], 2);
}
var Result = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render]]);

export { Result as default };
//# sourceMappingURL=result2.mjs.map
