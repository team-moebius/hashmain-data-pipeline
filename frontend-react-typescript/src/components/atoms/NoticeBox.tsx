import React from 'react';
import { AlertType } from 'react-alert';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';
import { green } from '@material-ui/core/colors';
import SnackbarContent, { SnackbarContentProps } from '@material-ui/core/SnackbarContent';

const useBackgroundColorStyles = makeStyles(theme => ({
  success: { backgroundColor: green[600] },
  error: { backgroundColor: theme.palette.error.dark },
  info: { backgroundColor: theme.palette.primary.main },
}));

export interface NoticeBoxProps extends SnackbarContentProps {
  noticeType: AlertType;
}

const NoticeBox: React.FC<NoticeBoxProps> = props => {
  const { className, noticeType, style, ...rest } = props;
  const backgroundClasses = useBackgroundColorStyles();
  const classNameByType = backgroundClasses[noticeType];

  return <SnackbarContent className={classNames(classNameByType, className)} {...rest} />;
};

NoticeBox.defaultProps = {
  noticeType: 'info',
};

export default NoticeBox;
