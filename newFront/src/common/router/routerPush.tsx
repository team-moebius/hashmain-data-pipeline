// import * as React from 'react'
import { RouteComponentProps } from 'react-router-dom'

export default {
  push: (props: RouteComponentProps<{ /* postId: string */ }>, path: string) => {
    // const nextPostId = Number(props.match.params.postId) + 1
    props.history.push(`/${path}`)
  }
}
