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
      '@black': '#B7C8F5',

      '@table-row-hover-bg': '#264B80',
      '@table-selected-row-hover-bg': '#264B80',

      '@table-header-bg-sm': '#173456',
      '@table-header-color': '#B7C8F5'
    }
  })
)
