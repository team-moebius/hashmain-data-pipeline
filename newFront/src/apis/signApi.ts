import { getInstance } from '../common/common'

const instant = getInstance()

export const getDuplicateApi = (email: string) => {
  const result = instant.get(`members/duplicate/${email}`)
  return result
}

export const getSignUpApi = (mail: string, usrName: string, pwd: string) => {
  const result = instant.post('members/signup', {
    signupDto: {
      email: mail,
      name: usrName,
      password: pwd
    }
  })
  return result
}
