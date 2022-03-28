import type { MaybeRef } from '@vueuse/core';
declare type DeprecationParam = {
    from: string;
    replacement: string;
    scope: string;
    version: string;
    ref: string;
};
export declare const useDeprecated: ({ from, replacement, scope, version, ref }: DeprecationParam, condition: MaybeRef<boolean>) => void;
export {};
