import axios from 'axios'

// let instance = axios.create({
//   headers: {
//     post: {        // can be common or any other method
//       header1: 'value1'
//     }
//   }
// })

export const getTestApi = (date: string = '20200120') => {
  const result = axios.get(`http://www.samsungwelstory.com/menu/getSuwonMenuList.do?dt=${date}&hallNm=together`, {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  })
  return result
}
