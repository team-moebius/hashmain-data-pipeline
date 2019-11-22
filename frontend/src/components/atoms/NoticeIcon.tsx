import React from 'react';
import { AlertType } from 'react-alert';

import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import ErrorIcon from '@material-ui/icons/Error';
import InfoIcon from '@material-ui/icons/Info';
import { SvgIconProps } from '@material-ui/core/SvgIcon';

export interface NoticeIconProps extends SvgIconProps {
  noticeType: AlertType;
}

const NoticeIcon: React.FC<NoticeIconProps> = props => {
  const { noticeType, ...rest } = props;
  let Icon = null;

  switch (noticeType) {
    case 'success':
      Icon = CheckCircleIcon;
      break;
    case 'error':
      Icon = ErrorIcon;
      break;
    case 'info':
      Icon = InfoIcon;
      break;
    default:
      Icon = InfoIcon;
      break;
  }
  return <Icon {...rest} />;
};

NoticeIcon.defaultProps = {
  noticeType: 'info',
};

export default NoticeIcon;
