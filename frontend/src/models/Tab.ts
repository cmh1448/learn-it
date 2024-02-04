import { JSX } from "solid-js";

export interface Tab {
  title: string;
  icon?: string;
  component: () => JSX.Element;
}