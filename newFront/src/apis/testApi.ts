import axios from 'axios'

const commonURL = 'http://dev-api.hashmainpro.com'
const instant = axios.create({
  baseURL: commonURL,
  timeout: 5000
})


export const getTestApi = (date: string = 'asdf@asdf.com') => {
  const result = instant.get(`members/duplicate/${date}`)
  return result
}
