declare const _default: import("vue").DefineComponent<{
    readonly animated: import("../../../utils").BuildPropReturn<BooleanConstructor, false, unknown, unknown, unknown>;
    readonly count: import("../../../utils").BuildPropReturn<NumberConstructor, 1, unknown, unknown, unknown>;
    readonly rows: import("../../../utils").BuildPropReturn<NumberConstructor, 3, unknown, unknown, unknown>;
    readonly loading: import("../../../utils").BuildPropReturn<BooleanConstructor, true, unknown, unknown, unknown>;
    readonly throttle: import("../../../utils").BuildPropReturn<NumberConstructor, unknown, unknown, unknown, unknown>;
}, {
    ns: {
        namespace: import("vue").ComputedRef<string>;
        b: (blockSuffix?: string) => string;
        e: (element?: string | undefined) => string;
        m: (modifier?: string | undefined) => string;
        be: (blockSuffix?: string | undefined, element?: string | undefined) => string;
        em: (element?: string | undefined, modifier?: string | undefined) => string;
        bm: (blockSuffix?: string | undefined, modifier?: string | undefined) => string;
        bem: (blockSuffix?: string | undefined, element?: string | undefined, modifier?: string | undefined) => string;
        is: {
            (name: string, state: boolean | undefined): string;
            (name: string): string;
        };
    };
    uiLoading: import("vue").Ref<boolean>;
}, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
    readonly animated: import("../../../utils").BuildPropReturn<BooleanConstructor, false, unknown, unknown, unknown>;
    readonly count: import("../../../utils").BuildPropReturn<NumberConstructor, 1, unknown, unknown, unknown>;
    readonly rows: import("../../../utils").BuildPropReturn<NumberConstructor, 3, unknown, unknown, unknown>;
    readonly loading: import("../../../utils").BuildPropReturn<BooleanConstructor, true, unknown, unknown, unknown>;
    readonly throttle: import("../../../utils").BuildPropReturn<NumberConstructor, unknown, unknown, unknown, unknown>;
}>>, {
    loading: import("../../../utils").BuildPropType<BooleanConstructor, unknown, unknown>;
    rows: number;
    animated: import("../../../utils").BuildPropType<BooleanConstructor, unknown, unknown>;
    count: number;
    throttle: number;
}>;
export default _default;
