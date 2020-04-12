import {
  HTS_TRADE_INFO_REQUESTED,
  HTS_TRADE_INFO_SUCCESS,
  HTS_TRADE_INFO_FAILED,
  HTS_TRADE_ORDER_REQUESTED,
  HTS_TRADE_ORDER_SUCCESS,
  HTS_TRADE_ORDER_FAILED,
  HTS_API_KEY_REQUESTED,
  HTS_API_KEY_SUCCESS,
  HTS_API_KEY_FAILED,
  HTS_API_KEY_RESET,
  HTS_API_KEY_STATE_CHANGE,
  HTS_MARKET_INFO_REQUESTED,
  HTS_MARKET_INFO_SUCCESS,
  HTS_MARKET_INFO_FAILED,
  HTS_MONEYTRAY_UNIT_CHANGE,
  HTS_STD_UNIT_CHANGE,
  HTS_ASSETS_REQUESTED,
  HTS_ASSETS_SUCCESS,
  HTS_ASSETS_FAILED,
  HTS_EXCHANGE_UPDATE
} from '../actionCmds/htsActionCmd'

interface IHtsInfoActionType { type: typeof HTS_TRADE_INFO_REQUESTED, menuType: string }
export const htsInfoActionType = (param: IHtsInfoActionType): IHtsInfoActionType => (
  { type: param.type, menuType: param.menuType }
)

interface IHtsInfoSuccessActionType { type: typeof HTS_TRADE_INFO_SUCCESS, htsData: {}, manageData?: {}[] }
export const htsInfoSuccessActionType = (param: IHtsInfoSuccessActionType): IHtsInfoSuccessActionType => (
  { type: param.type, htsData: param.htsData, manageData: param.manageData }
)

interface IHtsInfoFailedActionType { type: typeof HTS_TRADE_INFO_FAILED, msg: string }
export const htsInfoFailedActionType = (param: IHtsInfoFailedActionType): IHtsInfoFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface IHtsOrderActionType { type: typeof HTS_TRADE_ORDER_REQUESTED, dtos: Array<Object> }
export const htsOrderActionType = (param: IHtsOrderActionType): IHtsOrderActionType => (
  { type: param.type, dtos: param.dtos }
)

interface IHtsOrderSuccessActionType { type: typeof HTS_TRADE_ORDER_SUCCESS, htsData: {}, manageData?: {}[] }
export const htsOrderSuccessActionType = (param: IHtsOrderSuccessActionType): IHtsOrderSuccessActionType => (
  { type: param.type, htsData: param.htsData, manageData: param.manageData }
)

interface IHtsOrderFailedActionType { type: typeof HTS_TRADE_ORDER_FAILED, msg: string }
export const htsOrderFailedActionType = (param: IHtsOrderFailedActionType): IHtsOrderFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface IHtsAPIKeyActionType { type: typeof HTS_API_KEY_REQUESTED, restType: { type: string, data?: any }}
export const htsAPIKeyActionType = (param: IHtsAPIKeyActionType): IHtsAPIKeyActionType => (
  { type: param.type, restType: param.restType }
)

interface IHtsAPIKeySuccessActionType { type: typeof HTS_API_KEY_SUCCESS, answer: string | Object }
export const htsAPIKeySuccessActionType = (param: IHtsAPIKeySuccessActionType): IHtsAPIKeySuccessActionType => (
  { type: param.type, answer: param.answer }
)

interface IHtsAPIKeyFailedActionType { type: typeof HTS_API_KEY_FAILED, msg: string }
export const htsAPIKeyFailedActionType = (param: IHtsAPIKeyFailedActionType): IHtsAPIKeyFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface IHtsAPIKeyResetActionType { type: typeof HTS_API_KEY_RESET }
export const htsAPIKeyResetActionType = (param: IHtsAPIKeyResetActionType): IHtsAPIKeyResetActionType => (
  { type: param.type }
)

interface IHtsAPIKeyStateActionType { type: typeof HTS_API_KEY_STATE_CHANGE, apiKeyState: number }
export const htsAPIKeyStateActionType = (param: IHtsAPIKeyStateActionType): IHtsAPIKeyStateActionType => (
  { type: param.type, apiKeyState: param.apiKeyState }
)

interface IHtsMarketActionType { type: typeof HTS_MARKET_INFO_REQUESTED, exchange: string }
export const htsMarketActionActionType = (param: IHtsMarketActionType): IHtsMarketActionType => (
  { type: param.type, exchange: param.exchange }
)

interface IHtsMarketSuccessActionType { type: typeof HTS_MARKET_INFO_SUCCESS, marketData: [any] }
export const htsMarketSuccessActionType = (param: IHtsMarketSuccessActionType): IHtsMarketSuccessActionType => (
  { type: param.type, marketData: param.marketData }
)

interface IHtsMarketFailedActionType { type: typeof HTS_MARKET_INFO_FAILED, msg: string }
export const htsMarketFailedActionType = (param: IHtsMarketFailedActionType): IHtsMarketFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface IHtsMoenytrayChangeActionType { type: typeof HTS_MONEYTRAY_UNIT_CHANGE, monetaryUnit: string }
export const htsMoneytrayChangeActionType = (param: IHtsMoenytrayChangeActionType): IHtsMoenytrayChangeActionType => (
  { type: param.type, monetaryUnit: param.monetaryUnit }
)

interface IHtsStdUnitChangeActionType { type: typeof HTS_STD_UNIT_CHANGE, stdUnit: string }
export const htsStdUnitChangeActionType = (param: IHtsStdUnitChangeActionType): IHtsStdUnitChangeActionType => (
  { type: param.type, stdUnit: param.stdUnit }
)

interface IHtsAssetsActionType { type: typeof HTS_ASSETS_REQUESTED, exchange: string }
export const htsAssetsActionType = (param: IHtsAssetsActionType): IHtsAssetsActionType => (
  { type: param.type, exchange: param.exchange }
)

interface IHtsAssetsSuccessActionType { type: typeof HTS_ASSETS_SUCCESS, assetsData: any }
export const htsAssetsSuccessActionType = (param: IHtsAssetsSuccessActionType): IHtsAssetsSuccessActionType => (
  { type: param.type, assetsData: param.assetsData }
)

interface IHtsAssetsFailedActionType { type: typeof HTS_ASSETS_FAILED, msg: string }
export const htsAssetsFailedActionType = (param: IHtsAssetsFailedActionType): IHtsAssetsFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface IHtsExchangeActionType { type: typeof HTS_EXCHANGE_UPDATE, exchange: string }
export const htsExchangeActionType = (param: IHtsExchangeActionType): IHtsExchangeActionType => (
  { type: param.type, exchange: param.exchange }
)

export type htsTypes = IHtsInfoActionType | IHtsInfoSuccessActionType | IHtsInfoFailedActionType
  | IHtsOrderActionType | IHtsOrderSuccessActionType | IHtsOrderFailedActionType | IHtsExchangeActionType
  | IHtsAPIKeyActionType | IHtsAPIKeySuccessActionType | IHtsAPIKeyFailedActionType
  | IHtsAPIKeyResetActionType | IHtsAPIKeyStateActionType | IHtsMoenytrayChangeActionType
  | IHtsMarketActionType | IHtsMarketSuccessActionType | IHtsMarketFailedActionType
  | IHtsStdUnitChangeActionType | IHtsAssetsActionType | IHtsAssetsSuccessActionType | IHtsAssetsFailedActionType
