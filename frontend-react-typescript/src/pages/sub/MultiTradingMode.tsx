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

interface MultiTradingModeProps {}

interface MultiTradingModeState {
  fetching: boolean;
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
      fetching: false,
      orderData: {},
    };
  }

  componentDidMount = () => {
    ajax
      .get('/api/orders')
      .then(response => {
        this.originalOrderData = Object.freeze(this.convertOrderData(response.data));
        this.setState({ orderData: { ...this.originalOrderData } });
      })
      .catch(error => {
        // this.props.alert.error('Order data load fail');
      });
  };

  convertOrderData = (data: OrderData[]) => {
    return data.reduce((accumulator: { [key: string]: OrderData }, data: OrderData) => {
      accumulator[data.id] = { ...data, estimatedTotalPrice: Math.round(data.price * data.volume) };
      return accumulator;
    }, {});
  };

  classifyFetchingOrderData = (orderData: OrderData[]) => {
    const saleOrderData: OrderData[] = [];
    const purchaseOrderData: OrderData[] = [];
    const stoplossOrderData: OrderData[] = [];

    orderData.forEach(orderDatum => {
      switch (orderDatum.orderPosition) {
        case 'SALE':
          saleOrderData.push(orderDatum);
          break;
        case 'PURCHASE':
          purchaseOrderData.push(orderDatum);
          break;
        case 'STOPLOSS':
          stoplossOrderData.push(orderDatum);
          break;
      }
    });

    return { saleOrderData, purchaseOrderData, stoplossOrderData };
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

  onClickHeadLayerAddIcon = () => {
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
    const filteredData = this.classifyFetchingOrderData(Object.values(this.state.orderData));

    return (
      <div>
        <Grid<OrderData>
          columns={this.dataColumns}
          rowClassNameFunc={this.getRowClassName}
          rows={filteredData.saleOrderData}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
        <Grid<OrderData>
          columns={this.dataColumns}
          rowClassNameFunc={this.getRowClassName}
          rows={filteredData.purchaseOrderData}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
        <Grid<OrderData>
          columns={this.dataColumns}
          rowClassNameFunc={this.getRowClassName}
          rows={filteredData.stoplossOrderData}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
      </div>
    );
  }
}

export default MultiTradingMode;
