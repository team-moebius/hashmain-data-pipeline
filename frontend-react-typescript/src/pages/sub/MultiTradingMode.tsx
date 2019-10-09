import * as React from 'react';
import _ from 'lodash';

import ajax from 'utils/Ajax';
import Grid, { GridData } from 'components/organisms/Grid';
import { TableColum } from 'components/molecules/TableHeadLayer';

import 'assets/scss/MultiTradingMode.scss';

interface OrderData extends GridData {
  eventType: 'READ' | 'UPDATE' | 'CREATE' | 'DELETE';
  exchange: 'UPBIT';
  estimatedTotalPrice: number;
  orderPosition: 'SALE' | 'PURCHASE' | 'STOPLOSS';
  orderType: 'MARKET' | 'LIMIT';
  price: number;
  percentage: number;
  symbol: string;
  volume: number;
}

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
  selectedCurrency: string;
  fetching: boolean;
  assetData: { [key: string]: AssetData };
  orderData: { [key: string]: OrderData };
}

class MultiTradingMode extends React.Component<MultiTradingModeProps, MultiTradingModeState> {
  private dataColumns: TableColum[] = [
    { id: 'orderPosition', label: '주문 포지션', align: 'left' },
    {
      id: 'orderType',
      label: '모드 변경',
      align: 'left',
      sortable: true,
      onClickCell: (col, rowId) => this.toggleOrderType(col, rowId),
    },
    {
      id: 'price',
      label: '이익실현 지정가',
      align: 'right',
      sortable: true,
      format: value => `${this.formatNumber(value)} KRW`,
    },
    {
      id: 'volume',
      label: '매도 수량',
      align: 'right',
      sortable: true,
      format: value => `${this.formatNumber(value)} XRP`,
    },
    {
      id: 'estimatedTotalPrice',
      label: '예상 주문총액',
      align: 'right',
      sortable: true,
      format: value => `${this.formatNumber(value)} KRW`,
    },
    {
      id: 'percentage',
      label: '주문 비율',
      align: 'right',
      sortable: true,
      format: value => `${this.formatNumber(value)} %`,
    },
  ];

  private originalOrderData: { [key: string]: OrderData } = {};

  private formatNumber = (num: number) => num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');

  private toggleOrderType = (col: TableColum, rowId: string) => {
    const selectedData = this.state.orderData[rowId];
    // DELETE 상태에선 현재 값 Update 불가능
    if (selectedData.eventType !== 'DELETE') {
      const newOrderType = selectedData.orderType === 'LIMIT' ? 'MARKET' : 'LIMIT';
      const isUpdated = this.originalOrderData[rowId].orderType !== newOrderType;
      const value: OrderData = { ...selectedData, eventType: isUpdated ? 'UPDATE' : 'READ', orderType: newOrderType };
      this.updateRow(selectedData.id, value);
    }
  };

  constructor(props: MultiTradingModeProps) {
    super(props);

    this.state = {
      selectedCurrency: 'KRW',
      fetching: false,
      assetData: {},
      orderData: {},
    };
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
    return;
  };

  onClickPurchaseGridAddIcon = () => {
    return;
  };

  onClickStoplossGridAddIcon = () => {
    return;
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

  render() {
    const data = _.groupBy(Object.values(this.state.orderData), data => data.eventType === 'CREATE');
    const newData = _.groupBy(data['true'], data => data.orderPosition);
    const viewData = _.groupBy(data['false'], data => data.orderPosition);

    return (
      <div>
        <Grid<OrderData>
          columns={this.dataColumns}
          rowClassNameFunc={this.getRowClassName}
          rows={viewData['SALE']}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickSaleGridAddIcon}
        >
          {newData['SALE'] && newData['SALE'].map(data => {})}
        </Grid>
        <Grid<OrderData>
          columns={this.dataColumns}
          rowClassNameFunc={this.getRowClassName}
          rows={viewData['PURCHASE']}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickPurchaseGridAddIcon}
        >
          {newData['PURCHASE'] && newData['PURCHASE'].map(data => {})}
        </Grid>
        <Grid<OrderData>
          columns={this.dataColumns}
          rowClassNameFunc={this.getRowClassName}
          rows={viewData['STOPLOSS']}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickStoplossGridAddIcon}
        >
          {newData['STOPLOSS'] && newData['STOPLOSS'].map(data => {})}
        </Grid>
      </div>
    );
  }
}

export default MultiTradingMode;
