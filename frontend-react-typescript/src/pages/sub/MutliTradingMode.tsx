import * as React from 'react';
import Grid, { GridData } from 'components/organisms/Grid';
import { TableColum } from 'components/molecules/TableHeadLayer';
import ajax from 'utils/Ajax';

interface OrderData extends GridData {
  title: string;
  targetCost: number;
  estimatedCost: number;
  sellCost: number;
  percentage: number;
}

function createData(
  id: string,
  status: 'add' | 'delete' | 'default',
  title: string,
  targetCost: number,
  estimatedCost: number,
  sellCost: number,
  percentage: number
): OrderData {
  return { id, status, title, targetCost, estimatedCost, sellCost, percentage };
}

const data = [
  createData('1', 'default', '1차 이익실현', 1, 1, 1, 1),
  createData('0', 'default', '2차 이익실현', 2, 2, 2, 0),
];

interface MultiTradingModeProps {}

interface MultiTradingModeState {
  orderData: OrderData[];
}

class MultiTradingMode extends React.Component<MultiTradingModeProps, MultiTradingModeState> {
  private static dataColumns: TableColum[] = [
    { id: 'status', label: '주문 포지션', align: 'left' },
    { id: 'title', label: '이익실현 지정가', align: 'left' },
    { id: 'targetCost', label: '예상 주문총액', align: 'right', sortable: true },
    { id: 'estimatedCost', label: '매도 수량', align: 'right' },
    { id: 'sellCost', label: 'sellCost', align: 'right' },
    { id: 'percentage', label: 'percentage', align: 'right', sortable: true, format: value => `${value}%` },
  ];

  constructor(props: MultiTradingModeProps) {
    super(props);
  }

  componentDidMount = () => {
    ajax
      .get('/api/orders')
      .then(response => {
        console.log(response);
        // this.props.alert.success('등록 성공');
      })
      .catch(error => {
        // this.props.alert.error('Order data load fail');
      });
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
          rows={data}
          style={{ height: '215px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
        <Grid<OrderData>
          columns={MultiTradingMode.dataColumns}
          rows={data}
          style={{ height: '215px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
        <Grid<OrderData>
          columns={MultiTradingMode.dataColumns}
          rows={data}
          style={{ height: '215px' }}
          onClickRowDeleteIcon={this.onClickRowDeleteIcon}
          onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
        />
      </div>
    );
  }
}

export default MultiTradingMode;
