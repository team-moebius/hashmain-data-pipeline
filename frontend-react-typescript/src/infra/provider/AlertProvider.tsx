import React from 'react';
import { transitions, positions, Provider, AlertComponentPropsWithStyle } from 'react-alert';

import Notice from 'components/molecules/Notice';

const ALERT_OPTIONS = {
  WIDTH: 350,
  TIMEOUT: 2500,
  TRANSITION: transitions.FADE,
  OFFSET: '80px 0px 15px 0px',
  POSITION: positions.TOP_CENTER,
};

// react-alert 에 의존성이 존재하는 코드 Layer 라 별도로 view 파일 처리하지 마십시오
const AlertTemplate = ({ style, options, message, close }: AlertComponentPropsWithStyle) => (
  <div style={{ width: ALERT_OPTIONS.WIDTH, ...style, zIndex: 1 }}>
    <Notice noticeType={options.type || 'info'} message={message} onClose={close} />
  </div>
);

interface AlertProivderProps {
  children: React.ReactNode;
}

const AlertProvider: React.FC<AlertProivderProps> = props => (
  <Provider
    template={AlertTemplate}
    timeout={ALERT_OPTIONS.TIMEOUT}
    transition={ALERT_OPTIONS.TRANSITION}
    offset={ALERT_OPTIONS.OFFSET}
    position={ALERT_OPTIONS.POSITION}
  >
    {props.children}
  </Provider>
);

export default AlertProvider;
