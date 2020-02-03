const { override, fixBabelImports, addLessLoader } = require('customize-cra')

module.exports = override(
  fixBabelImports('import', {
    libraryName: 'antd',
    libraryDirectory: 'es',
    style: true
  }),
  addLessLoader({
    javascriptEnabled: true,
    modifyVars: {
      '@primary-color': '#FF3A7D',
      '@body-background': '#151544',
      '@component-background': '#101037',
      '@input-bg': '#1A1C4B',
      '@input-color': '#B7C8F5',
      '@input-placeholder-color': 'hsv(0, 0, 75%)',
      '@border-color-base': '#101037',
      '@checkbox-check-color': '#101037'
    }
  })
)
