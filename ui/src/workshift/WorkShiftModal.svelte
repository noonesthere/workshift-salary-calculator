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
  class="rounded-2xl p-0 shadow-xl w-full max-w-xl"
>
  <WorkShiftForm {onSave} />
</dialog>

<style>
  dialog::backdrop {
    background: rgba(0, 0, 0, 0.4);
  }
</style>
