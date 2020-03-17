import * as React from 'react';

import DeleteIcon from '@material-ui/icons/DeleteOutlined';

import Paper from 'components/atoms/Paper';
import Table from 'components/atoms/Table';
import TableToolbar, { TableToolbarProps } from 'components/molecules/TableToolbar';
import TableHeadLayer, { TableColumn } from 'components/molecules/TableHeadLayer';
import TableBody from 'components/atoms/TableBody';
import TableBodyRow from 'components/atoms/TableBodyRow';
import TableBodyCell from 'components/atoms/TableBodyCell';
import IconButton from 'components/atoms/IconButton';
import Checkbox from 'components/atoms/Checkbox';
import Input from 'components/atoms/Input';

export interface GridData {
  id: string;
}

function desc<T extends GridData>(a: T, b: T, orderBy: keyof T) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function stableSort<T extends GridData>(array: T[], cmp: (a: T, b: T) => number) {
  const stabilizedThis = array.map((el, index) => [el, index] as [T, number]);
  stabilizedThis.sort((a, b) => {
    const order = cmp(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map(el => el[0]);
}

function getSorting<T extends GridData>(order: 'asc' | 'desc', orderBy: keyof T): (a: T, b: T) => number {
  return order === 'desc' ? (a, b) => desc(a, b, orderBy) : (a, b) => -desc(a, b, orderBy);
}

interface GridProps<T extends GridData> {
  columns: TableColumn[];
  className?: string;
  rowClassNameFunc?: (rowId: string) => string;
  rows?: T[];
  newRows?: T[];
  toolbarProps?: TableToolbarProps;
  order?: 'asc' | 'desc';
  orderBy?: keyof T;
  style?: React.CSSProperties;
  onClickRow?: (event: React.MouseEvent<unknown>, rowId: string) => void;
  onClickRowDeleteIcon?: (e: React.MouseEvent<unknown>, rowId: string) => void;
  onClickHeadLayerAddIcon?: (e: React.MouseEvent<unknown>) => void;
}

interface GridState<T extends GridData> {
  order: 'asc' | 'desc';
  orderBy: keyof T;
}

class Grid<T extends GridData> extends React.Component<GridProps<T>, GridState<T>> {
  public constructor(props: GridProps<T>) {
    super(props);
    this.state = {
      order: this.props.order ? this.props.order : 'asc',
      orderBy: this.props.orderBy ? this.props.orderBy : 'id',
    };
  }

  private handleRequestSort = (e: React.MouseEvent<unknown>, property: keyof T) => {
    const isDesc = this.state.orderBy === property && this.state.order === 'desc';

    this.setState({ order: isDesc ? 'asc' : 'desc', orderBy: property });
  };

  private onClickRow = (rowId: string) => (e: React.MouseEvent<unknown>) => {
    if (this.props.onClickRow) {
      this.props.onClickRow(e, rowId);
    }
  };

  private onClickCell = (col: TableColumn, rowId: string) => (e: React.MouseEvent<unknown>) => {
    e.preventDefault();

    if (col.onClickCell) {
      col.onClickCell(col, rowId);
    }
  };

  private onChangeNewRowCell = (col: TableColumn, rowId: string) => (e: React.ChangeEvent<HTMLInputElement>) => {
    if (col.newRowProps && col.newRowProps.onChange) {
      col.newRowProps.onChange(e, { column: col, rowId });
    }
  };

  private onClickRowDeleteIcon = (rowId: string) => (e: React.MouseEvent<unknown>) => {
    if (this.props.onClickRowDeleteIcon) {
      this.props.onClickRowDeleteIcon(e, rowId);
    }
  };

  private getRowClassName = (rowId: string) => {
    return this.props.rowClassNameFunc ? this.props.rowClassNameFunc(rowId) : '';
  };

  public render() {
    const rows = this.props.rows ? this.props.rows : [];
    return (
      <Paper className={this.props.className} style={{ width: '100%', ...this.props.style }}>
        {this.props.toolbarProps && <TableToolbar {...this.props.toolbarProps} />}
        <div style={{ overflowX: 'auto' }}>
          <Table aria-labelledby="tableTitle" style={{ minWidth: 750 }}>
            <TableHeadLayer
              columns={this.props.columns}
              order={this.state.order}
              orderBy={this.state.orderBy}
              onRequestSort={this.handleRequestSort}
              onClickAddIcon={this.props.onClickHeadLayerAddIcon}
            />
            <TableBody>
              {this.props.newRows &&
                this.props.newRows.map(row => (
                  <TableBodyRow className={this.getRowClassName(row.id)} key={row.id} onClick={this.onClickRow(row.id)}>
                    {this.props.onClickRowDeleteIcon && (
                      <TableBodyCell>
                        <IconButton
                          icon={<DeleteIcon aria-label="delete" />}
                          size="small"
                          onClick={this.onClickRowDeleteIcon(row.id)}
                        />
                      </TableBodyCell>
                    )}
                    {this.props.columns.map((col: TableColumn) => {
                      // @ts-ignore
                      const label = row[col.id];
                      const formattedLabel = col.format ? col.format(label) : label;

                      return col.checkbox ? (
                        <TableBodyCell
                          align={col.align}
                          key={col.id}
                          padding="checkbox"
                          style={{ cursor: col.onClickCell && 'pointer', ...col.style }}
                          onClick={this.onClickCell(col, row.id)}
                        >
                          <Checkbox {...col.checkbox} />
                        </TableBodyCell>
                      ) : (
                        <TableBodyCell
                          align={col.align}
                          key={col.id}
                          padding={col.disablePadding ? 'none' : 'default'}
                          style={{ cursor: col.onClickCell && 'pointer', ...col.style }}
                          onClick={this.onClickCell(col, row.id)}
                        >
                          {col.newRowProps && col.newRowProps.type === 'input' ? (
                            <Input
                              type={col.numeric ? 'number' : ''}
                              defaultValue={col.numeric ? 0 : ''}
                              inputProps={{ style: { padding: '4px', textAlign: col.numeric ? 'right' : undefined } }}
                              margin="none"
                              onChange={this.onChangeNewRowCell(col, row.id)}
                            />
                          ) : (
                            formattedLabel
                          )}
                        </TableBodyCell>
                      );
                    })}
                  </TableBodyRow>
                ))}
              {stableSort(rows, getSorting(this.state.order, this.state.orderBy)).map(row => (
                <TableBodyRow
                  className={this.getRowClassName(row.id)}
                  hover
                  key={row.id}
                  onClick={this.onClickRow(row.id)}
                >
                  {this.props.onClickRowDeleteIcon && (
                    <TableBodyCell>
                      <IconButton
                        icon={<DeleteIcon aria-label="delete" />}
                        size="small"
                        onClick={this.onClickRowDeleteIcon(row.id)}
                      />
                    </TableBodyCell>
                  )}
                  {this.props.columns.map((col: TableColumn) => {
                    // @ts-ignore
                    const label = row[col.id];

                    return col.checkbox ? (
                      <TableBodyCell
                        align={col.align}
                        key={col.id}
                        padding="checkbox"
                        style={{ cursor: col.onClickCell && 'pointer', ...col.style }}
                        onClick={this.onClickCell(col, row.id)}
                      >
                        <Checkbox {...col.checkbox} />
                      </TableBodyCell>
                    ) : (
                      <TableBodyCell
                        align={col.align}
                        key={col.id}
                        padding={col.disablePadding ? 'none' : 'default'}
                        style={{ cursor: col.onClickCell && 'pointer', ...col.style }}
                        onClick={this.onClickCell(col, row.id)}
                      >
                        {col.format ? col.format(label) : label}
                      </TableBodyCell>
                    );
                  })}
                </TableBodyRow>
              ))}
              {this.props.children}
            </TableBody>
          </Table>
        </div>
      </Paper>
    );
  }
}

export default Grid;
