import React from 'react';
import { AlertType } from 'react-alert';
import classNames from 'classnames';

import CloseIcon from '@material-ui/icons/Close';
import { makeStyles } from '@material-ui/core/styles';

import IconButton from 'components/atoms/IconButton';
import NoticeBox from 'components/atoms/NoticeBox';
import NoticeIcon from 'components/atoms/NoticeIcon';
import Text from 'components/atoms/Text';

const useStyles = makeStyles(theme => ({
  icon: {
    color: 'white',
    fontSize: 20,
  },
  noticeIcon: {
    opacity: 0.9,
    marginRight: theme.spacing(1),
  },
  message: {
    color: 'white',
    display: 'flex',
    alignItems: 'center',
  },
}));

interface NoticeProps {
  noticeType: AlertType;
  message: React.ReactNode;
  style?: React.CSSProperties;
  onClose: (e: React.MouseEvent<HTMLElement, MouseEvent>) => void;
}

const Notice: React.FC<NoticeProps> = props => {
  const classes = useStyles();
  const { noticeType, message, onClose, ...rest } = props;

  return (
    <NoticeBox
      action={
        <IconButton
          aria-label="close"
          icon={<CloseIcon aria-label="close" className={classes.icon} />}
          onClick={onClose}
        />
      }
      aria-describedby="client-snackbar"
      noticeType={noticeType}
      message={
        <Text id="client-snackbar" className={classes.message} variant="caption">
          <NoticeIcon className={classNames(classes.icon, classes.noticeIcon)} noticeType={noticeType} />
          {message}
        </Text>
      }
      {...rest}
    />
  );
};

export default Notice;
