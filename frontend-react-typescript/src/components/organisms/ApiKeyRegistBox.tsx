import * as React from 'react';
import MuiButton from '@material-ui/core/Button';

import Input from 'components/atoms/Input';
import Text from 'components/atoms/Text';
import TextForButton from 'components/atoms/TextForButton';

interface ApiKeyRegistBoxProps {
  onClickRegistApiKeyButton: (data: object) => void;
  onClickViewMyApiKeyButton: () => void;
}

const ApiKeyRegistBox: React.FC<ApiKeyRegistBoxProps> = props => {
  const botNameRef = React.createRef<any>();
  const accessKeyRef = React.createRef<any>();
  const secretKeyRef = React.createRef<any>();
  const onClickRegistApiKeyButton = (data: object) => {
    props.onClickRegistApiKeyButton({
      accessKey: accessKeyRef.current.value,
      secretKey: secretKeyRef.current.value,
      name: botNameRef.current.value,
      exchange: 'UPBIT',
    });
  };
  return (
    <div>
      <Text align="center" style={{ paddingBottom: '15px' }} variant="h6">
        API KEY 등록하기
      </Text>
      <Input
        autoComplete="off"
        autoFocus
        inputRef={botNameRef}
        name="Bot name"
        placeholder="Bot name"
      />
      <Input
        autoComplete="off"
        inputRef={accessKeyRef}
        name="Access Key"
        placeholder="Access Key"
      />
      <Input
        autoComplete="off"
        inputRef={secretKeyRef}
        name="Secret Key"
        placeholder="Secret Key"
      />
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
      <MuiButton
        color="secondary"
        // disabled={this.props.pending}
        fullWidth
        style={{ marginBottom: '8px' }}
        size="large"
        type="submit"
        variant="contained"
        onClick={onClickRegistApiKeyButton}
      >
        <TextForButton variant="h6">API KEY 등록</TextForButton>
      </MuiButton>
      <MuiButton
        color="secondary"
        // disabled={this.props.pending}
        fullWidth
        size="large"
        type="submit"
        variant="contained"
        onClick={props.onClickViewMyApiKeyButton}
      >
        <TextForButton variant="h6">내 API KEY 확인</TextForButton>
      </MuiButton>
    </div>
  );
};

export default ApiKeyRegistBox;
