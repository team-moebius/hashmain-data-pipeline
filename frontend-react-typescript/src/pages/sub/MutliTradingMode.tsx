import * as React from 'react';
import Grid, { GridData } from 'components/organisms/Grid';
import { TableColum } from 'components/molecules/TableHeadLayer';

interface Data extends GridData {
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
): Data {
  return { id, status, title, targetCost, estimatedCost, sellCost, percentage };
}

const data = [
  createData('1', 'default', '1차 이익실현', 1, 1, 1, 1),
  createData('0', 'default', '2차 이익실현', 2, 2, 2, 0),
];

interface MultiTradingModeProps {}

interface MultiTradingModeState {}

class MultiTradingMode extends React.Component<MultiTradingModeProps, MultiTradingModeState> {
  private static dataColumns: TableColum[] = [
    { id: 'status', label: 'status', align: 'left' },
    { id: 'title', label: 'title', align: 'left' },
    { id: 'targetCost', label: 'targetCost', align: 'right', sortable: true },
    { id: 'estimatedCost', label: 'estimatedCost', align: 'right' },
    { id: 'sellCost', label: 'sellCost', align: 'right' },
    { id: 'percentage', label: 'percentage', align: 'right', sortable: true, format: value => `${value}%` },
  ];

  constructor(props: MultiTradingModeProps) {
    super(props);

    this.state = {};
  }

  onClickRowDeleteIcon = () => {
    return;
  };

  onClickHeadLayerAddIcon = () => {
    return;
  };

  render() {
    return (
      <Grid<Data>
        columns={MultiTradingMode.dataColumns}
        rows={data}
        onClickRowDeleteIcon={this.onClickRowDeleteIcon}
        onClickHeadLayerAddIcon={this.onClickHeadLayerAddIcon}
      />
    );
  }
}

export default MultiTradingMode;
