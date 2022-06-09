import { FC } from "react"
import { InputContainer } from "./text-input-component.style"

export interface TextInputProps {
  inputName: string
  inputLabel: string
  inputValue: string
}
const TextInput:FC<TextInputProps> = ({
  inputName,
  inputLabel,
  inputValue
}) => (
    <InputContainer>
    <label>{inputLabel}</label>
    <input type="text" name={inputName} value={inputValue} required />
    </InputContainer>
)

export default TextInput
