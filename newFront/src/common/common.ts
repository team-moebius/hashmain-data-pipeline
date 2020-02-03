import axios from 'axios'

const baseURL = 'http://api.cryptoboxglobal.com'

export function getInstance() {
  return axios.create({
    baseURL: baseURL,
    timeout: 5000
  })
}
