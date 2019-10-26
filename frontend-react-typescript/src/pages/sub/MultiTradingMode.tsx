import * as React from 'react';
import shortid from 'shortid';
import _ from 'lodash';

import Button from 'components/atoms/Button';
import Grid, { GridData } from 'components/organisms/Grid';
import { TableColum, NewRowParams } from 'components/molecules/TableHeadLayer';
import ajax from 'utils/Ajax';

import 'assets/scss/MultiTradingMode.scss';

type EventType = 'READ' | 'UPDATE' | 'CREATE' | 'DELETE';
type ExchangeType = 'UPBIT';
type OrderType = 'MARKET' | 'LIMIT';
type OrderPositionType = 'SALE' | 'PURCHASE' | 'STOPLOSS';

interface OrderData extends GridData {
  eventType: EventType;
  exchange: ExchangeType;
  estimatedTotalPrice: number;
  orderPosition: OrderPositionType;
  orderType: OrderType;
  price: number;
  percentage: number;
  symbol: 'KRW';
  volume: number;
}

type GridColumn = 'orderPosition' | 'orderType' | 'price' | 'volume' | 'estimatedTotalPrice' | 'percentage';

interface AssetData {
  balance: number;
  currency: string;
  locked: number;
}

interface AjaxData {
  assets: AssetData[];
  orders: OrderData[];
}

interface MultiTradingModeProps {}

interface MultiTradingModeState {
  selectedCurrency: 'KRW';
  fetching: boolean;
  assetData: { [key: string]: AssetData };
  orderData: { [key: string]: OrderData };
}

class MultiTradingMode extends React.Component<MultiTradingModeProps, MultiTradingModeState> {
  private commonColumns: { [key in GridColumn]: TableColum } = {
    orderPosition: { id: 'orderPosition', label: '', align: 'left' },
    orderType: {
      id: 'orderType',
      label: '모드 변경',
      align: 'left',
      sortable: true,
      onClickCell: (col, rowId) => this.toggleOrderType(col, rowId),
    },
    price: {
      id: 'price',
      label: '',
      align: 'right',
      sortable: true,
      style: { width: 160 },
      numeric: true,
      newRowProps: {
        type: 'input',
        onChange: (e, row) => this.onChangeNewRowValue(e, row),
      },
      format: value => `${this.formatNumber(value)} KRW`,
    },
    volume: {
      id: 'volume',
      label: '',
      align: 'right',
      numeric: true,
      sortable: true,
      style: { width: 160 },
      newRowProps: {
        type: 'input',
        onChange: (e, row) => this.onChangeNewRowValue(e, row),
      },
      format: value => `${this.formatNumber(value)} XRP`,
    },
    estimatedTotalPrice: {
      id: 'estimatedTotalPrice',
      label: '예상 주문총액',
      align: 'right',
      sortable: true,
      style: { width: 160 },
      numeric: true,
      format: value => `${this.formatNumber(value)} KRW`,
    },
    percentage: {
      id: 'percentage',
      label: '주문 비율',
      align: 'right',
      sortable: true,
      style: { width: 140 },
      numeric: true,
      newRowProps: {
        type: 'input',
        onChange: (e, row) => this.onChangeNewRowValue(e, row),
      },
      format: value => `${this.formatNumber(value)} %`,
    },
  };
  private sellGridColums: TableColum[] = [];
  private stoplossGridColums: TableColum[] = [];
  private purchaseGridColums: TableColum[] = [];
  private originalOrderData: { [key: string]: OrderData } = {};

  private formatNumber = (num: number) => num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');

  private toggleOrderType = (col: TableColum, rowId: string) => {
    const selectedData = this.state.orderData[rowId];
    const newOrderType = selectedData.orderType === 'LIMIT' ? 'MARKET' : 'LIMIT';
    // DELETE 상태에선 현재 값 Update 불가능
    if (selectedData.eventType === 'READ' || selectedData.eventType === 'UPDATE') {
      const isUpdated = this.originalOrderData[rowId].orderType !== newOrderType;
      const value: OrderData = { ...selectedData, eventType: isUpdated ? 'UPDATE' : 'READ', orderType: newOrderType };
      this.updateRow(selectedData.id, value);
    } else if (selectedData.eventType === 'CREATE') {
      const value: OrderData = { ...selectedData, orderType: newOrderType };
      this.updateRow(selectedData.id, value);
    }
  };

  private onChangeNewRowValue = (e: React.ChangeEvent<HTMLInputElement>, newRow: NewRowParams) => {
    this.updateNewRowValue(e.currentTarget.value, newRow);
  };

  private updateNewRowValue = (value: string, newRow: NewRowParams) => {
    const currentRow = this.state.orderData[newRow.rowId];
    const editedRow = { ...currentRow, [newRow.colum.id]: value };
    this.setState({ orderData: { ...this.state.orderData, [newRow.rowId]: editedRow } }, () =>
      console.log(this.state.orderData)
    );
  };

  private setGridColumLabel = () => {
    let sellColumns, purchaseColumns, stoplossColums;
    sellColumns = _.cloneDeep(this.commonColumns);
    sellColumns.orderPosition.label = '이익실현 매도';
    sellColumns.price.label = '이익실현 지정가';
    sellColumns.volume.label = '매도 수량';
    this.sellGridColums = Object.values(sellColumns);

    purchaseColumns = _.cloneDeep(this.commonColumns);
    purchaseColumns.orderPosition.label = '지정가 매수';
    purchaseColumns.price.label = '매수 지정가';
    purchaseColumns.volume.label = '먜수 수량';
    this.purchaseGridColums = Object.values(purchaseColumns);

    stoplossColums = _.cloneDeep(this.commonColumns);
    stoplossColums.orderPosition.label = '스탑 로스';
    stoplossColums.price.label = '매도 지정가';
    stoplossColums.volume.label = '매도 수량';
    this.stoplossGridColums = Object.values(stoplossColums);
  };

  constructor(props: MultiTradingModeProps) {
    super(props);

    this.setGridColumLabel();
    this.state = {
      selectedCurrency: 'KRW',
      fetching: false,
      assetData: {},
      orderData: {},
    };
    this.updateNewRowValue = _.debounce(this.updateNewRowValue, 500);
  }

  componentDidMount = () => {
    ajax
      .get('/api/orders')
      .then(response => {
        const responseData = this.convertData(response.data);
        this.originalOrderData = Object.freeze(responseData.orderData);
        this.setState({ ...responseData });
      })
      .catch(error => {
        // this.props.alert.error('Order data load fail');
      });
  };

  convertData = (data: AjaxData) => {
    const assetData: { [key: string]: AssetData } = data.assets.reduce(
      (accumulator: { [key: string]: AssetData }, data: AssetData) => {
        accumulator[data.currency] = { ...data };
        return accumulator;
      },
      {}
    );

    const orderData: { [key: string]: OrderData } = data.orders.reduce(
      (accumulator: { [key: string]: OrderData }, data: OrderData) => {
        const estimatedTotalPrice = Math.round(data.price * data.volume);
        const currentCurrencyBalance = assetData[this.state.selectedCurrency].balance;
        const percentage =
          Math.round(
            (data.orderPosition === 'PURCHASE'
              ? estimatedTotalPrice / currentCurrencyBalance
              : data.volume / currentCurrencyBalance) * 100
          ) / 100;

        accumulator[data.id] = { ...data, estimatedTotalPrice, percentage };
        return accumulator;
      },
      {}
    );

    return { assetData, orderData };
  };

  onClickRowDeleteIcon = (e: React.MouseEvent<unknown, MouseEvent>, rowId: string) => {
    e.preventDefault();

    const selectedData = this.state.orderData[rowId];
    const originalData = this.originalOrderData[rowId];

    if (selectedData.eventType === 'READ') {
      const value: OrderData = { ...selectedData, eventType: 'DELETE' };
      this.updateRow(selectedData.id, value);
    } else if (selectedData.eventType === 'CREATE') {
      this.setState({ orderData: _.omit(this.state.orderData, selectedData.id) });
    } else if (selectedData.eventType === 'DELETE') {
      const value: OrderData = { ...selectedData, eventType: 'READ' };
      this.updateRow(selectedData.id, value);
    } else if (selectedData.eventType === 'UPDATE') {
      const value: OrderData = { ...originalData, eventType: 'DELETE' };
      this.updateRow(selectedData.id, value);
    }
  };

  updateRow = (rowId: string, value: OrderData) => {
    this.setState({ orderData: { ...this.state.orderData, [rowId]: value } });
  };

  onClickSaleGridAddIcon = () => {
    this.createNewRow('SALE');
  };

  onClickPurchaseGridAddIcon = () => {
    this.createNewRow('PURCHASE');
  };

  onClickStoplossGridAddIcon = () => {
    this.createNewRow('STOPLOSS');
  };

  createNewRow = (orderPosition: OrderPositionType) => {
    const rowId = shortid.generate();
    const newRow: OrderData = {
      orderPosition,
      id: rowId,
      exchange: 'UPBIT',
      eventType: 'CREATE',
      estimatedTotalPrice: 0,
      orderType: 'LIMIT',
      price: 0,
      percentage: 0,
      volume: 0,
      symbol: 'KRW',
    };

    this.setState({ orderData: { ...this.state.orderData, [rowId]: newRow } });
  };

  getRowClassName = (rowId: string) => {
    const rowEventType = this.state.orderData[rowId].eventType;

    switch (rowEventType) {
      case 'CREATE':
        return 'trading-grid-row__created';
      case 'DELETE':
        return 'trading-grid-row__deleted';
      case 'UPDATE':
        return 'trading-grid-row__updated';
    }
    return '';
  };

  onClickRegistOrderButton = () => {};

  render() {
    const dataByCreated = _.groupBy(Object.values(this.state.orderData), data => data.eventType === 'CREATE');
    const newData = _.groupBy(dataByCreated['true'], data => data.orderPosition);
    const viewData = _.groupBy(dataByCreated['false'], data => data.orderPosition);

    return (
      <div>
        <Grid<OrderData>
          columns={this.sellGridColums}
          rowClassNameFunc={this.getRowClassName}
          rows={viewData['SALE']}
          newRows={newData['SALE']}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickSaleGridAddIcon}
        />
        <Grid<OrderData>
          columns={this.purchaseGridColums}
          rowClassNameFunc={this.getRowClassName}
          rows={viewData['PURCHASE']}
          newRows={newData['PURCHASE']}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickPurchaseGridAddIcon}
        />
        <Grid<OrderData>
          columns={this.stoplossGridColums}
          rowClassNameFunc={this.getRowClassName}
          rows={viewData['STOPLOSS']}
          newRows={newData['STOPLOSS']}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickStoplossGridAddIcon}
        />
        <Button>주 문 등 록</Button>
      </div>
    );
  }
}

export default MultiTradingMode;
