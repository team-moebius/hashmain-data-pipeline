import { getInstance } from '../common/common'

const instant = getInstance()

export const getDuplicateApi = (email: string) => {
  const result = instant.get(`members/duplicate/${email}`)
  return result
}

export const postSignUpApi = (mail: string, name: string, password: string) => {
  const result = instant.post('members/signup', {
    email: mail,
    name: name,
    password: password
  })
  return result
}

export const postSignInApi = (mail: string, password: string) => {
  const result = instant.post('members', {
    email: mail,
    password: password
  })
  return result
}
