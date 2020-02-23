import axios from 'axios'
import { notification } from 'antd'

const commonURL = 'http://dev-api.hashmainpro.com'

export function getInstance() {
  return axios.create({
    baseURL: commonURL,
    timeout: 5000
  })
}

export function openNotification(type: string, msg: string, des?: string) {
  notification[type]({
    message: msg,
    description: des,
    className: `customNoti${type}`,
    duration: 2
  })
}
