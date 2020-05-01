// Must be make a pure function

interface ITestActionType { type:string, age: number }
export const testAction = (dispatch: (param: ITestActionType) => void, param: ITestActionType) => {
  dispatch({ type: param.type, age: param.age })
}

interface ITestApiActionType { type: string, date: string }
export const testApiAction = (dispatch: (param: ITestApiActionType) => void, param: ITestApiActionType) => {
  dispatch({ type: param.type, date: param.date })
}
