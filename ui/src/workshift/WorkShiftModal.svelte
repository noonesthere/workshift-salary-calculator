<script lang="ts">
  import { onMount } from "svelte";
  import WorkShiftForm from "./WorkShiftForm.svelte";
  import type { WorkShift } from "../api/types";

  let { open, onClose, onSave } = $props<{
    open: boolean;
    onClose?: () => void;
    onSave?: (payload: WorkShift) => void;
  }>();

  let dialog: HTMLDialogElement;

  // Open / close controlled by prop
  $effect(() => {
    if (!dialog) return;

    if (open && !dialog.open) {
      dialog.showModal();
    }

    if (!open && dialog.open) {
      dialog.close();
    }
  });

  function handleClose() {
    onClose?.();
  }
</script>

<dialog
  bind:this={dialog}
  onclose={handleClose}
  class="p-0 rounded-2xl shadow-xl w-full max-w-xl"
>
  <WorkShiftForm {onSave} />
</dialog>

<style>
  dialog {
    border: none;
    padding: 0;
    margin: auto;
  }

  dialog[open] {
    display: flex;
    flex-direction: column;
  }

  dialog::backdrop {
    background: rgba(0, 0, 0, 0.4);
    backdrop-filter: blur(2px);
  }
</style>
