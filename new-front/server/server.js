const compression = require('compression')
const express = require('express')
const logger = require('morgan')
const path = require('path')
const fs = require('fs')

const app = express()

app.use(compression())
app.use(logger('dev'))
app.use(express.static(path.join(__dirname, '..', 'build')))
const port = process.env.PORT || 3000

app.get('*', (req, res) => {
  fs.readFile('./build/index.html', (error, data) => {
    if (error) {
      console.error(error)
    } else {
      res.writeHead(200, { 'Content-Type': 'text/html' })
      res.end(data)
    }
  })
})

app.listen(port, () => {
  console.log(`App listening on port ${port}`)
})

module.exports = app
