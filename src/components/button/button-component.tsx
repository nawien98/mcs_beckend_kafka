import { FC } from "react"
import { ButtonContainer } from "./button-component.style"

export interface ButtonProps {
  buttonName: string
  onClick: (event: React.MouseEvent<HTMLElement>) => void
}
const Button:FC<ButtonProps> = ({
  buttonName,
  onClick
}) => (
  <ButtonContainer>
    <input type="submit" onClick={onClick}>{buttonName}</input>
  </ButtonContainer>
)

export default Button
