// This file is a kind of business component, so this is classified as organisms.
import * as React from 'react';

import Input from 'components/atoms/Input';
import Text from 'components/atoms/Text';
import Button from 'components/atoms/Button';
import InputValidator from 'utils/InputValidator';

type ApiKeyRegistPayloadType = 'accessKey' | 'secretKey' | 'name' | 'exchange';
type ApiKeyRegistValidationType = 'accessKey' | 'secretKey' | 'name';
type ApiKeyRegistPayload = { [key in ApiKeyRegistPayloadType]?: string };
type ApiKeyRegistErrorState = { [key in ApiKeyRegistValidationType]?: string };

interface ApiKeyRegistBoxProps {
  className?: string;
  pending?: boolean;
  onSubmit: (data: ApiKeyRegistPayload) => void;
  onClickViewMyApiKeyButton: () => void;
}

interface ApiKeyRegistBoxState {
  errors: ApiKeyRegistErrorState;
}

class ApiKeyBox extends React.Component<ApiKeyRegistBoxProps, ApiKeyRegistBoxState> {
  private accessKeyRef = React.createRef<any>();
  private secretKeyRef = React.createRef<any>();
  private botNameRef = React.createRef<any>();

  constructor(props: ApiKeyRegistBoxProps) {
    super(props);
    this.state = { errors: {} };
  }

  onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (this.isValid()) {
      this.props.onSubmit({
        accessKey: this.accessKeyRef.current.value,
        secretKey: this.secretKeyRef.current.value,
        name: this.botNameRef.current.value,
        exchange: 'UPBIT',
      });
    }
  };

  isValid = () => {
    let accessKeyErrorText = '';
    let secretKeyErrorText = '';
    let botNameErrorText = '';

    if (InputValidator.isBlank(this.accessKeyRef.current.value)) {
      accessKeyErrorText = 'Access key를 입력해 주세요';
    }
    if (InputValidator.isBlank(this.secretKeyRef.current.value)) {
      secretKeyErrorText = 'Secret key를 입력해 주세요';
    }
    if (InputValidator.isBlank(this.botNameRef.current.value)) {
      botNameErrorText = 'Bot name을 입력해 주세요';
    }

    this.setState({
      errors: {
        accessKey: accessKeyErrorText,
        secretKey: secretKeyErrorText,
        name: botNameErrorText,
      },
    });

    return (
      InputValidator.isBlank(accessKeyErrorText) &&
      InputValidator.isBlank(secretKeyErrorText) &&
      InputValidator.isBlank(botNameErrorText)
    );
  };

  render() {
    return (
      <form className={this.props.className} onSubmit={this.onSubmit}>
        <Text align="center" gutterBottom style={{ fontWeight: 'bold', paddingBottom: '6px' }} variant="subtitle2">
          API KEY 등록하기
        </Text>
        <Input
          autoComplete="off"
          error={!!this.state.errors.name}
          helperText={this.state.errors.name}
          inputRef={this.botNameRef}
          name="Bot name"
          placeholder="Bot name"
          style={{ marginBottom: '8px' }}
        />
        <Input
          autoComplete="off"
          error={!!this.state.errors.accessKey}
          helperText={this.state.errors.accessKey}
          inputRef={this.accessKeyRef}
          name="Access Key"
          placeholder="Access Key"
          style={{ marginBottom: '8px' }}
        />
        <Input
          autoComplete="off"
          error={!!this.state.errors.secretKey}
          helperText={this.state.errors.secretKey}
          inputRef={this.secretKeyRef}
          name="Secret Key"
          placeholder="Secret Key"
        />
        <ul>
          <li>
            <Text gutterBottom variant="caption">
              API KEY 및 API SECRET KEY 관리에 유의하시기 바랍니다. 회원의 관리 소홀로 발생한 손실 및 유실에 ICORE.
              Inc는 책임지지 않습니다.
            </Text>
          </li>
          <li>
            <Text gutterBottom variant="caption">
              API KEY를 생성할 때는 꼭 자산 출금 권한 체크를 해제 하였는지 확인 하시기 바랍니다.
            </Text>
          </li>
          <li>
            <Text gutterBottom variant="caption">
              Crypto Box HTS는 회원이 설정한 주문에 대해서 발동 할 수 있도록 24시간 자동 매매 서비스를 제공합니다.
            </Text>
          </li>
          <li>
            <Text gutterBottom variant="caption">
              매매 포지션을 설정하는 것은 전적으로 회원의 선택에 따른 것으로서 가상화폐 시세 하락으로 거래 결과가 손해로
              이어질 수 있으며, ICORE. Inc는 이에 대하여 책임을 지지 않습니다.
            </Text>
          </li>
        </ul>
        <Button disabled={this.props.pending} type="submit" style={{ marginBottom: '8px' }}>
          <Text variant="button">API KEY 등록</Text>
        </Button>
        <Button disabled={this.props.pending} onClick={this.props.onClickViewMyApiKeyButton}>
          <Text variant="button">내 API KEY 확인</Text>
        </Button>
      </form>
    );
  }
}

export default ApiKeyBox;
