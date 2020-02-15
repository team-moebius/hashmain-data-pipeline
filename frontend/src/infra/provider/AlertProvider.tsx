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

// This component depends on react-alert. Do not process view file here independently.
const AlertTemplate = ({ style, options, message, close }: AlertComponentPropsWithStyle) => (
  <div style={{ width: ALERT_OPTIONS.WIDTH, ...style, zIndex: 1 }}>
    <Notice noticeType={options.type || 'info'} message={message} onClose={close} />
  </div>
);

interface AlertProviderProps {
  children: React.ReactNode;
}

const AlertProvider: React.FC<AlertProviderProps> = props => (
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
