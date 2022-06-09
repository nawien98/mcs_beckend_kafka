import { FC } from "react"

export interface RadioButtonProps {
  buttonName: string
  buttonValue: string
  onClick: (event: React.MouseEvent<HTMLElement>) => void
}
const RadioButton:FC<RadioButtonProps> = ({
  buttonName,
  buttonValue,
  onClick
}) => (
  <span>
    <input type="radio" name={buttonName} id={buttonValue}  autoComplete="off"  onClick={onClick}  />
    <label htmlFor={buttonValue}>{buttonValue}</label>
  </span>
)

export default RadioButton
