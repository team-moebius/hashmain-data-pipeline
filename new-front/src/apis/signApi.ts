import { getInstance } from '../common/common'

const instant = getInstance()

export const getDuplicateApi = (email: string) => {
  const result = instant.get(`members/duplicate/${email}`)
  return result
}

export const postSignUpApi = (mail: string, usrName: string, pwd: string) => {
  const result = instant.post('members/signup', {
    email: mail,
    name: usrName,
    password: pwd
  })
  return result
}

export const postSignInApi = (mail: string, pwd: string) => {
  const result = instant.post('members', {
    email: mail,
    password: pwd
  })
  return result
}
