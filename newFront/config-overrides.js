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
      '@primary-color': '#1E8CDE',
      '@body-background': '#172E4F',
      '@component-background': '#13253F',
      '@input-bg': '#173456',
      '@input-color': '#FFFFFF',
      '@input-placeholder-color': '#8494a6',
      '@border-color-base': '#13253F',
      '@checkbox-check-color': '#13253F',
      '@black': '#B7C8F5'
    }
  })
)
