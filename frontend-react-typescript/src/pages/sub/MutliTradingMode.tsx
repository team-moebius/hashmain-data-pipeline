import * as React from 'react';
import Grid, { GridData } from 'components/organisms/Grid';
import { TableColum } from 'components/molecules/TableHeadLayer';
import ajax from 'utils/Ajax';

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
  saleOrderData: OrderData[];
  purchaseOrderData: OrderData[];
  stoplossOrderData: OrderData[];
}

class MultiTradingMode extends React.Component<MultiTradingModeProps, MultiTradingModeState> {
  private static dataColumns: TableColum[] = [
    { id: 'orderPosition', label: '주문 포지션', align: 'left' },
    { id: 'orderType', label: '모드 변경', align: 'left', sortable: true },
    {
      id: 'price',
      label: '이익실현 지정가',
      align: 'right',
      sortable: true,
      format: value => `${MultiTradingMode.formatNumber(value)} KRW`,
    },
    {
      id: 'volume',
      label: '매도 수량',
      align: 'right',
      sortable: true,
      format: value => `${MultiTradingMode.formatNumber(value)} XRP`,
    },
    {
      id: 'estimatedTotalPrice',
      label: '예상 주문총액',
      align: 'right',
      sortable: true,
      format: value => `${MultiTradingMode.formatNumber(value)} KRW`,
    },
    {
      id: 'percentage',
      label: '주문 비율',
      align: 'right',
      sortable: true,
      format: value => `${MultiTradingMode.formatNumber(value)} %`,
    },
  ];

  private static formatNumber = (num: number) => num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');

  constructor(props: MultiTradingModeProps) {
    super(props);

    this.state = {
      fetching: false,
      saleOrderData: [],
      purchaseOrderData: [],
      stoplossOrderData: [],
    };
  }

  componentDidMount = () => {
    ajax
      .get('/api/orders')
      .then(response => {
        console.log(response);
        const orderData = this.calculateTotalPrice(response.data);
        const { saleOrderData, purchaseOrderData, stoplossOrderData } = this.classifyFetchingOrderData(orderData);
        this.setState({ saleOrderData, purchaseOrderData, stoplossOrderData });
      })
      .catch(error => {
        // this.props.alert.error('Order data load fail');
      });
  };

  calculateTotalPrice = (orderData: OrderData[]) => {
    return orderData.map(data => {
      data.estimatedTotalPrice = Math.round(data.price * data.volume);
      return data;
    });
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

  onClickRowDeleteIcon = () => {
    return;
  };

  onClickHeadLayerAddIcon = () => {
    return;
  };

  render() {
    return (
      <div>
        <Grid<OrderData>
          columns={MultiTradingMode.dataColumns}
          rows={this.state.saleOrderData}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
        <Grid<OrderData>
          columns={MultiTradingMode.dataColumns}
          rows={this.state.purchaseOrderData}
          style={{ marginBottom: '80px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
        <Grid<OrderData>
          columns={MultiTradingMode.dataColumns}
          rows={this.state.stoplossOrderData}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
      </div>
    );
  }
}

export default MultiTradingMode;
