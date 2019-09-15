import * as React from 'react';

import Input from 'components/atoms/Input';
import Text from 'components/atoms/Text';
import Button from 'components/atoms/Button';

interface ApiKeyRegistBoxProps {
  pending?: boolean;
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
      <Text align="center" style={{ paddingBottom: '15px' }} variant="subtitle1">
        API KEY 등록하기
      </Text>
      <Input autoComplete="off" inputRef={botNameRef} name="Bot name" placeholder="Bot name" />
      <Input autoComplete="off" inputRef={accessKeyRef} name="Access Key" placeholder="Access Key" />
      <Input autoComplete="off" inputRef={secretKeyRef} name="Secret Key" placeholder="Secret Key" />
      <ul>
        <li>
          <Text gutterBottom variant="caption">
            * API KEY 및 API SECRET KEY 관리에 유의하시기 바랍니다. 회원의 관리 소홀로 발생한 손실 및 유실에 ICORE.
            Inc는 책임지지 않습니다.
          </Text>
        </li>
        <li>
          <Text gutterBottom variant="caption">
            * API KEY를 생성할 때는 꼭 자산 출금 권한 체크를 해제 하였는지 확인 하시기 바랍니다.
          </Text>
        </li>
        <li>
          <Text gutterBottom variant="caption">
            * Crypto Box HTS는 회원이 설정한 주문에 대해서 발동 할 수 있도록 24시간 자동 매매 서비스를 제공합니다.
          </Text>
        </li>
        <li>
          <Text gutterBottom variant="caption">
            * 매매 포지션을 설정하는 것은 전적으로 회원의 선택에 따른 것으로서 가상화폐 시세 하락으로 거래 결과가 손해로
            이어질 수 있으며, ICORE. Inc는 이에 대하여 책임을 지지 않습니다.
          </Text>
        </li>
      </ul>
      <Button disabled={props.pending} style={{ marginBottom: '8px' }} onClick={onClickRegistApiKeyButton}>
        <Text variant="button">API KEY 등록</Text>
      </Button>
      <Button disabled={props.pending} onClick={props.onClickViewMyApiKeyButton}>
        <Text variant="button">내 API KEY 확인</Text>
      </Button>
    </div>
  );
};

export default ApiKeyRegistBox;
