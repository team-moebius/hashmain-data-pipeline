import * as React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/LibraryAddOutlined';

import Checkbox, { CheckboxProps } from 'components/atoms/Checkbox';
import TableHead from 'components/atoms/TableHead';
import TableRow from 'components/atoms/TableRow';
import TableCell from 'components/atoms/TableCell';
import TableSortLabel from 'components/atoms/TableSortLabel';
import IconButton from 'components/atoms/IconButton';

const useStyles = makeStyles(theme => ({
  sortSupporter: {
    border: 0,
    clip: 'rect(0 0 0 0)',
    height: 1,
    margin: -1,
    overflow: 'hidden',
    padding: 0,
    position: 'absolute',
    top: 20,
    width: 1,
  },
}));

export interface TableColum {
  align?: 'inherit' | 'left' | 'center' | 'right' | 'justify';
  checkbox?: CheckboxProps;
  disablePadding?: boolean;
  id: string;
  label?: string;
  minWidth?: number;
  sortable?: boolean;
}

interface TableHeadLayerProps {
  columns: TableColum[];
  order?: 'asc' | 'desc';
  orderBy?: any;
  onRequestSort?: (e: React.MouseEvent<unknown>, colId: any) => void;
  onClickAddIcon?: (e: React.MouseEvent<unknown>) => void;
}

const CheckboxCell: React.FC<TableColum> = props => (
  <TableCell key={props.id} style={{ minWidth: props.minWidth }} padding="checkbox">
    <Checkbox {...props.checkbox} />
    {props.label}
  </TableCell>
);

const TableHeadLayer: React.FC<TableHeadLayerProps> = props => {
  const classes = useStyles();
  const onClickSortHandler = (colId: string) => (e: React.MouseEvent<HTMLSpanElement, MouseEvent>) => {
    props.onRequestSort && props.onRequestSort(e, colId);
  };

  return (
    <TableHead>
      <TableRow>
        {props.onClickAddIcon && (
          <TableCell>
            <IconButton icon={<AddIcon aria-label="add" />} onClick={props.onClickAddIcon} />
          </TableCell>
        )}
        {props.columns.map(col =>
          col.checkbox ? (
            <CheckboxCell {...col} />
          ) : (
            <TableCell
              align={col.align}
              padding={col.disablePadding ? 'none' : 'default'}
              sortDirection={col.id === props.orderBy ? props.order : false}
              key={col.id}
            >
              {col.sortable ? (
                <TableSortLabel
                  active={props.orderBy === col.id}
                  direction={props.order}
                  onClick={onClickSortHandler(col.id)}
                >
                  {col.label}
                  {props.orderBy === col.id ? (
                    <span className={classes.sortSupporter}>
                      {props.order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                    </span>
                  ) : null}
                </TableSortLabel>
              ) : (
                col.label
              )}
            </TableCell>
          )
        )}
      </TableRow>
    </TableHead>
  );
};

export default TableHeadLayer;
