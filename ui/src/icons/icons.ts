const modules = import.meta.glob('./*.svg', {
  eager: true,
  query: '?raw',
  import: 'default'
}) as Record<string, string>;

const icons = Object.fromEntries(
  Object.entries(modules).map(([path, svg]) => [
    path.slice(2, -4), // remove "./" and ".svg"
    svg
  ])
);

export default icons;