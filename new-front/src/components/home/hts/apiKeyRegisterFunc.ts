import { HTS_API_KEY_REQUESTED, HTS_API_KEY_RESET } from '../../../actionCmds/htsActionCmd'
import { htsAPIKeyActionType, htsAPIKeyResetActionType } from '../../../actions/htsAction'
import { openNotification } from '../../../common/common'

export function apiKeyValidCheck(
  inputValue: { name: string, accessKey: string, secretKey: string},
  dispatch: any,
  isValid: boolean,
  exchange: string
): void {
  if (!inputValue.name || !inputValue.accessKey || !inputValue.secretKey) {
    openNotification('error', '모든 칸을 채워주세요.')
    return
  }
  if (isValid) {
    openNotification('error', '이미 유효성 검사를 하였습니다.')
    return
  }

  dispatch(htsAPIKeyActionType({
    type: HTS_API_KEY_REQUESTED,
    restType: {
      type: 'get',
      data: {
        accessKey: inputValue.accessKey,
        exchange: exchange.toUpperCase(),
        name: inputValue.name,
        secretKey: inputValue.secretKey
      }
    }
  }))
}

export function apiKeyRegister(
  registerValue: any, dispatch: any, isValid: boolean, setIsRegister: any, exchange: string
): void {
  if (!isValid) {
    openNotification('error', 'Key 유효성 체크를 해주세요')
    return
  }

  dispatch(htsAPIKeyActionType({
    type: HTS_API_KEY_REQUESTED,
    restType: {
      type: 'post',
      data: {
        accessKey: registerValue.accessKey,
        exchange: exchange.toUpperCase(),
        name: registerValue.name,
        secretKey: registerValue.secretKey
      }
    }
  }))
  dispatch(htsAPIKeyResetActionType({ type: HTS_API_KEY_RESET }))
  setIsRegister(false)
}
