export function mailCheck (id: string): boolean {
	const regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
  return regExp.test(id)
}

export function nameCheck (name: string): boolean {
  return name.length > 0
}

export function passwordCheck (password: string): boolean {
  const regExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,30}$/
	return regExp.test(password)
}

export function passwordConfirmCheck (password: string, confirmPassword: string): boolean {
  if (!password || !confirmPassword) return false
  return password === confirmPassword
}