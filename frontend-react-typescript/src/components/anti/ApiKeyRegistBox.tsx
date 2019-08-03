import * as React from 'react';
import MuiTypography from '@material-ui/core/Typography';

import Input from 'components/atoms/Input';
import Text from 'components/atoms/Text';

interface ApiKeyRegistBoxProps {}

const ApiKeyRegistBox: React.FC<ApiKeyRegistBoxProps> = props => {
  return (
    <div>
      <MuiTypography align="center" style={{ paddingBottom: '15px' }} variant="h6">
        API KEY 등록하기
      </MuiTypography>
      <Input autoComplete="off" autoFocus name="Bot name" placeholder="Bot name" />
      <Input autoComplete="off" autoFocus name="Access Key" placeholder="Access Key" />
      <Input autoComplete="off" autoFocus name="Secret Key" placeholder="Secret Key" />
      <ul>
        <li>
          <Text gutterBottom>
            * API KEY 및 API SECRET KEY 관리에 유의하시기 바랍니다. 회원의 관리 소홀로 발생한 손실
            및 유실에 대하여 ICORE. Inc는 책임지지 않습니다.
          </Text>
        </li>
        <li>
          <Text gutterBottom>
            * API KEY를 생성할 때는 꼭 자산 출금 권한 체크를 해제 하였는지 확인 하시기 바랍니다.
          </Text>
        </li>
        <li>
          <Text gutterBottom>
            * Crypto Box HTS는 회원이 설정한 주문에 대해서 발동 할 수 있도록 24시간 자동 매매
            서비스를 제공합니다.
          </Text>
        </li>
        <li>
          <Text gutterBottom>
            * 매매 포지션을 설정하는 것은 전적으로 회원의 선택에 따른 것으로서 가상화폐 시세
            하락으로 거래 결과가 손해로 이어질 수 있으며, ICORE. Inc는 이에 대하여 책임을 지지
            않습니다.
          </Text>
        </li>
      </ul>
    </div>
  );
};

export default ApiKeyRegistBox;
